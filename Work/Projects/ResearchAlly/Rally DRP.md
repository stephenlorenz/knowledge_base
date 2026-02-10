### Disaster Recovery Plan
  
This document describes the disaster recovery (DR) plan as it exists today for the Rally production environment. It reflects the current architecture, recovery objectives, data protection mechanisms, procedures, and known limitations given our cost constraints.  
  
---  
  
### 1) Recovery Objectives  
- Recovery Time Objective (RTO): 12 hours  
- Recovery Point Objective (RPO): Ideally 0; several hours of data loss may be acceptable  
  
Interpretation with current capabilities:  
- Instance-/AZ‑level database failures: RPO typically minutes to hours depending on the exact restore point chosen via RDS Point‑in‑Time Recovery (PITR) and snapshot timing; RTO well within 12 hours.  
- Region‑wide failures: Currently limited. See Limitations section.  
  
---  
  
### 2) System Overview (Production)  
- Region: AWS us‑east‑1  
- Compute: ECS Fargate  
  - Cluster: `rally-prod-cluster`  
  - Service: `rally-prod-service-fargate`  
  - Task Definition: `rally-prod-fargate` (two containers)  
    - `reverse-proxy`: nginx cache + thumbnail generation; image `676917001797.dkr.ecr.us-east-1.amazonaws.com/rally-aws-reverse-proxy-v5:latest`  
    - `webapp`: Spring Boot; image `676917001797.dkr.ecr.us-east-1.amazonaws.com/research-ally-prod:<tag>`  
- Application: Spring Boot (stateless), clustering via Hazelcast for session/caching and blue/green deploys; typically 1 task runs to contain cost  
- Database: PostgreSQL on AWS RDS  
  - Endpoint: `rally-prod-db.c2jt2a68uwxt.us-east-1.rds.amazonaws.com`  
  - Auth: IAM authentication (no passwords in app configs)  
- Messaging: Embedded JMS provider (no external broker dependency)  
- Configuration:  
  - S3 properties: `https://researchally.s3.us-east-1.amazonaws.com/properties/prod/application.properties`  
  - Env file for webapp task: `arn:aws:s3:::researchally/properties/prod-fargate/application.env`  
- CI/CD: GitHub Actions (builds and pushes images to ECR)  
- Monitoring/Logging: CloudWatch (application logs via awslogs driver, alarms on infra/app metrics) 
  
---  
  
### 3) Data Protection (Current Mechanisms)  
- AWS RDS Automated Backups and PITR:  
  - Automated snapshots enabled with standard retention (per RDS configuration)  
  - Supports restore to a specific point‑in‑time within the retention window  
- Manual Backups:  
  - Weekly full logical backup (`pg_dump`) executed every Sunday at 00:00 (midnight)  
  - Stored on MGB server, mgblxmghes1.parten (/apps/backup/data/rally-prod-backup.gpg).
- Application State:  
  - Stateless containers; nginx cache and thumbnail outputs are ephemeral and can be regenerated  
  - Hazelcast provides distributed session/caching; loss of a single task does not cause data loss in the database  
- Configuration/Secrets:  
  - S3‑hosted configuration files (properties and env); IAM auth used for RDS  
  
---  
  
### 4) DR Scenarios and Procedures  
  
#### Scenario A: Database instance failure (same region, single AZ)  
Goal: Restore service with RPO minutes–hours (depending on PITR), within RTO ≤ 12 hours.  
  
Steps:  
1. Incident handling  
   - Declare incident, page on‑call, open an incident channel, and freeze deploys.  
2. Restore RDS  
   - Use RDS Console or CLI to perform Point‑in‑Time Recovery (PITR) or restore from the most recent automated snapshot.  
   - Create a new RDS instance (temporary name `rally-prod-db-restore`).  
3. Validate database  
   - Run smoke queries to verify schema and recent data.  
4. Repoint application  
   - Update the database endpoint in `s3://researchally/properties/prod-fargate/application.env` (or relevant property file) to the new RDS endpoint.  
   - Force a new ECS deployment to pick up configuration:  
     - `aws ecs update-service --cluster rally-prod-cluster --service rally-prod-service-fargate --force-new-deployment --region us-east-1`  
5. Verify and scale  
   - Check `/ping` and basic user flows (login, read, write).  
   - Scale tasks if necessary.  
6. Post‑recovery  
   - Decommission the failed DB after ensuring the replacement is stable and backups are functioning.  
  
Estimated timing: Typically 1–4 hours depending on DB size and snapshot restore times; within the 12‑hour RTO.  
  
#### Scenario B: Data corruption / accidental destructive change  
Goal: Restore data to a point before corruption within RTO ≤ 12 hours; RPO depends on restore time selection.  
  
Steps:  
1. Quarantine  
   - Immediately stop write traffic (scale ECS service to 0 tasks or enable maintenance mode if available).  
2. Determine recovery point  
   - Identify the timestamp immediately preceding the bad change.  
3. PITR restore  
   - Restore RDS to the selected timestamp as a new instance.  
4. Validate  
   - Smoke queries to confirm absence of corruption.  
5. Repoint application  
   - Update the S3 env/properties with the new DB endpoint and force ECS redeploy (as above).  
6. Optional data reconciliation  
   - If necessary, export specific rows from restored instance and merge into current primary. Otherwise, fully switch to the restored instance.  
  
Estimated timing: Commonly 2–6 hours end‑to‑end depending on DB size and verification.  
  
#### Scenario C: ECS service/application failure (containers crash or misconfiguration)  
Goal: Restore app availability rapidly; no data loss.  
  
Steps:  
1. Redeploy last known good task definition  
   - Roll back to the previous stable image tag if the latest is faulty.  
2. Validate health  
   - Confirm `/ping` green; check logs for startup errors.  
1. If cluster infrastructure fault persists  
   - Recreate ECS service/ALB via IaC or Console within us‑east‑1.  
   - If regional control plane degradation is suspected, see Scenario D for regional limitations.  
  
Estimated timing: Typically minutes to 1–2 hours.  
  
#### Scenario D: Regional outage (us‑east‑1 unavailable)  
Current limitation: We run single‑region only. RDS automated snapshots and S3 properties are in us‑east‑1. Without pre‑copied cross‑region DB snapshots and replicated configs/artifacts, recovery in another region is not guaranteed within RTO.  
  
Contingent plan (best effort with current state):  
1. Assess whether S3 logical backups (`pg_dump`) and container images are accessible from another region (depends on where they are stored today).  
2. If available, stand up minimal infrastructure in a secondary region (VPC, ALB, ECS) and restore database from the latest accessible backup.  
3. Re‑deploy the application using the same task definition/images if present in the secondary region’s ECR; otherwise, pull from us‑east‑1 if service is reachable.  
4. Update DNS (Route53) to point to the new ALB.  
  
Note: This scenario is currently high risk and may exceed the 12‑hour RTO. See Limitations.  
  
---  
  
### 5) Validation and Smoke Tests  
After any recovery:  
- Application health: `/ping` returns UP.  
- User flows: login, key read, and at least one write operation succeed.  
- Database: application connects to the restored endpoint; error rates and latency normal.  
- nginx/thumbnail: thumbnails regenerate on demand; cache begins populating.  
  
---  
  
### 6) Monitoring and Alerting (Current)  
- CloudWatch alarms on ECS service health, container logs, CPU/memory, application 5xx where available.  
- Operational monitoring on RDS (availability, CPU/storage metrics).  
- On‑call notified via existing alarm routes.  
  
---  
  
### 7) Roles and Communication  
- Incident Commander: on‑call engineering lead (rotating)  
- DB Operations: on‑call engineer with RDS access  
- Application Operations: on‑call engineer for ECS/ECR/ALB and config  
- Communications: designated stakeholder liaison for internal status updates  
- All major DR actions are recorded in the incident channel with timestamps.  
  
---  
  
### 8) Backup and DR Testing Cadence  
- Backups  
  - Automated RDS backups: continuous (per retention policy)  
  - Manual logical backup: weekly `pg_dump` (Sunday 00:00)  
- DR Exercises  
  - As‑needed restore tests are performed during maintenance windows. Formal cadence is ad‑hoc at present.  
  
---  
  
### 9) Artifacts, Config, and Access  
- ECR holds application and proxy images; GitHub Actions builds and pushes.  
- S3 holds application properties and environment files for ECS tasks.  
- IAM roles:  
  - `ecsTaskExecutionRole` for task pulls/logging  
  - `rally-ecs-task-execution-role-prod` for runtime access as configured  
- Access to perform DR is restricted to on‑call engineers with MFA.  
  
---  
  
### 10) Assumptions and Current Limitations  
- Single‑AZ, single DB instance in us‑east‑1 due to budget constraints.  
- No dedicated read replica or Multi‑AZ standby.  
- No pre‑provisioned resources in a secondary region.  
- RDS automated backups and snapshots exist only in us‑east‑1; cross‑region copies are not currently configured.  
- Weekly logical backups exist; accessibility in a regional outage depends on where they are stored and KMS/key availability.  
- Result: The plan effectively covers instance-/AZ‑level failures and data corruption within RTO ≤ 12h and RPO of minutes–hours. Region‑wide outages remain a known risk and may exceed RTO.  
  
---  
  
### 11) Resource References (Production)  
- ECS Cluster: `rally-prod-cluster`  
- ECS Service: `rally-prod-service-fargate`  
- Task Definition Family: `rally-prod-fargate`  
- Containers:  
  - `reverse-proxy` (nginx cache + thumbnails)  
  - `webapp` (Spring Boot)  
- RDS Endpoint: `rally-prod-db.c2jt2a68uwxt.us-east-1.rds.amazonaws.com`  
- S3 Properties:  
  - `s3://researchally/properties/prod/application.properties`  
  - `s3://researchally/properties/prod-fargate/application.env`  
  
---  
  
### 12) Appendix: Example Commands (Reference)  
- Force redeploy after updating env/properties in S3:  
```  
aws ecs update-service \  
  --cluster rally-prod-cluster \  
  --service rally-prod-service-fargate \  
  --force-new-deployment \  
  --region us-east-1  
```  
- RDS Point‑in‑Time restore (within us‑east‑1):  
```  
aws rds restore-db-instance-to-point-in-time \  
  --source-db-instance-identifier rally-prod-db \  
  --target-db-instance-identifier rally-prod-db-restore \  
  --use-latest-restorable-time \  
  --region us-east-1  
# Or provide --restore-time "YYYY-MM-DDTHH:MM:SSZ"  
```  
  
---  
  
### Statement of Current DR Readiness  
With the current architecture and backups, we can recover from instance‑level database failures, data corruption, and application/service faults within the 12‑hour RTO and with RPO ranging from minutes to hours depending on the recovery point chosen. Region‑level disasters are a known gap; recovery under a full regional outage is best‑effort and may exceed RTO given that backups and artifacts are not pre‑replicated cross‑region at this time.