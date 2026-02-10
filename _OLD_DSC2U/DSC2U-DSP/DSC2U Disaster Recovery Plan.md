The DSC2U web application is a website that allows patients and caregivers of people with Down syndrome access to expert care plans and advice. 

### 1. Critical Components:

DSC2U consists of two separate web applications, the Sprout2pt0 form engine and the DSC2U website. DSC2U uses Sprout2pt0 behind the scenes to generate forms for users to complete. Upon form completion and submission, Sprout2pt0 generates dynamic output based on form values. In addition, both Sprout2pt0 and DSC2U use a PostgreSQL database, hosted in AWS RDS, and the NGINX web server as a reverse proxy.

- **Application Components:**
  - DSC2U web application
  - Sprout2pt0 web application
  
- **Supporting Components**
  - NGINX web server (reverse proxy)
  - NetScaler (load balancing)

- **Database Components:**
  - AWS RDS PostgreSQL database.

### 2. Servers:

#### Production Servers:
- phslxmghrp1
	- Red Hat Enterprise 8 Server
	- 4 CPU
	- 12G RAM
	- 128G storage
- phslxmghrp2
	- Red Hat Enterprise 8 Server
	- 4 CPU
	- 12G RAM
	- 128G storage
- AWS Hosted Database Server
	- AWS RDS PostgreSQL 15.5
	- 2 CPU
	- 2G RAM
	- "Unlimited" storage

#### Test Server:
- dsppcoritest (hosted by Research Computing)

### 3. Simplified Network Diagram:
![[DSC2U Simplified Network Diagram - legacy (2).png]]
- ##### Ports/Connections:
	- ###### Web Application Internal:
		- DSC2U web application: 8080
		- Sprout2p0 web application: 7080
	- ###### Web Application External:
		- DSC2U reverse proxy: 8443
		- Sprout2pt0 reverse proxy: 7443
	- ###### Web Application Other:
		- sshd: 22
		- Hazelcast (cluster/cache): 5701/5801
	- ###### AWS RDS Connection Settings:
		- **URL:** database-1.c3e0m2x2zq1s.us-east-1.rds.amazonaws.com
		- **Port:** 5432
		- **Database:** dsc2u_prod
		- **Username:** dsc2u_app_prod
		- **Password:** [****************]

### 4. Data Process Flow Diagram:
![[DSC2U Overview - no heading.png]]
### 5. Backups:

- **Database Backups:**
  - Automated backups for the AWS RDS PostgreSQL database are configured to run at 22:00 every evening.
  - Backups are encrypted at rest
  - Backups are stored for 7 days
  - AWS RDS restore points occur regularly throughout the day and are independent from formal nightly backups

- **Source Code Backups:**
  - Source code is backed up in the cloud in GitHub.
  - Source code is also replicated on developer workstations (and backed up there, as well)
  - GitHub Action Runners also cache 

### 6. Version Control:

- **Source Code Versioning:**
  - All source code is located in GitHub:
	  - DSC2U source code: https://github.com/MGH-LCS/dsc2u-website
	  - Sprout2pt0 source code: https://github.com/MGH-LCS/sprout2pt0
  - We use Git tags to mark important releases and milestones.

### 7. Building and Deploying

- The building of Sprout2pt0 and DSC2U are performed by Maven. For example,

```shell
mvn clean install
```

- GitHub Action scripts build the code on LCS hosted hardware, lcs106.partners.org.
- GitHub Action scripts are part of repository, inside .github directory per specification, e.g. `.github/workflows/maven-test.yml`
### 8. Monitoring and Alerts:

  - AWS CloudWatch Canneries continuously monitor DSC2U and Sprout2pt0 websites and database connectivity.
  - Canneries triggers alerts for potential issues which notify team.

### 9. High Availability:

- Both the DSC2U and Sprout2pt0 web applications run in a cluster.
- Configuration supports blue-green deployments where upgrades require no downtime.
- The LCS NetScaler balances traffic between the two application nodes.

### 10. Security Measures:

- **Security Considerations:**
  - All web application authentication and authorization events are logged to the audit_log table
  - Regularly audit and update security measures.
