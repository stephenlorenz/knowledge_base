Creating a comprehensive disaster recovery plan for a Spring Boot application hosted on-premises with an external AWS RDS PostgreSQL database and source code in GitHub involves several key steps. Here's a general guide to help you get started:# BEI Mobile App Monthly Maintenance
Name: <u>Stephen Lorenz</u>
Date: &nbsp;&nbsp;<u>August 7, 2024</u>

The BEI Mobile application servers run on Ubuntu 20.04.3 LTS hosted on AWS EC2 instances. Once a month the following tasks will be performed to the three BEI instances. These instances include:

| Name | Description | Instance ID | Public IP address |
|---|---|---|---|
| beiapp-staging-ec1 | Staging server | i-0afed8a389548cc63 | 3.21.92.194 |
| beiapp-prod-ecv1 | Prod server 1 | i-03b308fc318ec204c | 52.14.3.193 |
| beiapp-prod-ecv2 | Prod server 2 | i-0179464648da173a3 | 3.138.143.38 |


## Data, Software, Security and System checks

- [x]   Run [AWS Inspector](https://us-east-2.console.aws.amazon.com/inspector/home?region=us-east-2#/run) assessment 
	-   remediate any `high` or `critical` findings
- [x]  Check RDS backups/snapshots are working
- [x]  Check and update OS
	 - [x] Run `apt update`
     - [x] Run `apt upgrade`
     - [x] If the kernel is updated, reboot the server
- [x]   Check and update applications
- [x]   Check server usage
    - [x]  Disk
        -  If too high,  get rid of any old or outdated software.
    - [x]   CPU
        -   If too high, troubleshoot CPU utilization
    - [x]   RAM
        -   If too high, troubleshoot RAM utilization
    - [x]   Network
        -   If too high, troubleshoot network utilization
- [x] Review user accounts
- [x] Confirm rpush logs are being created in the ~/app/current/log directory
- [x] Free up server storage space
