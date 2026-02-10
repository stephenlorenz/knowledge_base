# Rally v2.1.0 - Deployment Notes

#### Use the following procedure to deploy Rally v2.1.0 to Production.

<br>

##### Deployment Preparation

The following steps need to wait until we are completely done testing, but could potentially occur well before we begin the deployment process.

1. Merge the Rally Git v2.1.0 branch with the master branch.

1. Update the `src/main/resources/version.properties` file, setting version to:

	```
	version=2.1.0.0-RELEASE
	```
	
1. Tag the state of the Git repository as `v2.1.0`:

	```git
	git tag v2.1.0
	```

1. Push this commit to GitHub


<br>

##### Deployment Process

1. Update DNS CNAME record in [AWS Route 53](https://console.aws.amazon.com/route53/v2/hostedzones#ListRecordSets/ZTEL1MLODQ7SI) for `rally-partners-prod.researchally.org` to point to:

	```
	rally-2-1-0-elb-534171683.us-east-1.elb.amazonaws.com
	```
	
1. Wait for the DNS change to propagate (about 60 seconds)

1. Shutdown the Production Rally instances by updating the AWS ECS `rally-prod-service` via the [AWS ECS Console](https://console.aws.amazon.com/ecs/v2/clusters/rally-prod-cluster/services/rally-prod-service/health?region=us-east-1), setting the desired number of running tasks to `0`.

1. SSH to the Rally 2.1.0 AWS EC2 staging instance:

	```bash
	ssh -i ~/.ssh/rallyE1.pem ec2-user@18.232.80.102
	```

1. Backup the production database:

	```bash
	$ sudo su
	$ cd /opt/backup/bin
	$ ./backupRallyPROD.sh
	```
		NOTE: The backup should take about 8 minutes.

1. Run the migration script on the production database as the *ec2-user*:

	```bash
	$ cd /home/ec2-user/migrate
	$ export CONFIG=/home/ec2-user/migrate/application-prod.properties
	$ ./run.sh
	```
		NOTE: The migration script should take about 3 minutes.

1. Start the Production Rally instances by updating the `rally-prod-service` via the [AWS ECS Console](https://console.aws.amazon.com/ecs/v2/clusters/rally-prod-cluster/services/rally-prod-service/health?region=us-east-1), setting the desired number of running tasks to `2`.

	Note, the instances will attempt to start but will continue to fail. This is OK and expected as we have not yet deployed the Rally v2.1.0 release.
	
1. From the GitHub [Rally Actions page](https://github.com/rallyforresearch/rally/actions), manually invoke a build/deploy of the master branch. 

	``NOTE: This process can take about 20-25 minutes.``

1. Reset DNS CNAME record in [AWS Route 53](https://console.aws.amazon.com/route53/v2/hostedzones#ListRecordSets/ZTEL1MLODQ7SI) for `rally-partners-prod.researchally.org` to point to:

	```
	rally-prod-application-1721326692.us-east-1.elb.amazonaws.com
	```

1. Wait for the DNS change to propagate (about 60 seconds).

1. Perform sanity checks with the Rally v2.1.0 in Production.

1. Done
	