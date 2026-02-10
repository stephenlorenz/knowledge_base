# Rally v2.1.0 - Deployment Notes
## Blue-Green Deploy

#### Use the following procedure to deploy Rally v2.1.0 to Production.

This deploy method uses a zero downtime approach, during which there will be about a minute where the database and the code base are out of sync. However, I don't expect this to break the majority of the site and the benefits outweigh the risks.

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

1. Prepare AWS ECS Task Definition

	- Go to `https://us-east-1.console.aws.amazon.com/ecs/home?region=us-east-1#/taskDefinitions`
	- Choose the `rally-prod` task definition
	- Choose the most recent task definition
	- Click on the `Create new revision` button
	- Scroll down to the `rally-prod-webapp` container and click on the link
	- Paste the image URI from the AWS ECR, e.g. 676917001797.dkr.ecr.us-east-1.amazonaws.com/rally-prod:RALLY-V2_1_0_0-RELEASE
	- Click on the `Update` button
	- Click on the `Create` button


<br>

##### Deployment Process

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

1. From the [GitHub Rally Actions page](https://github.com/rallyforresearch/rally/actions), manually invoke a build/deploy of the master branch. 

	``NOTE: This process can take about 20-25 minutes.``

1. Watch the production logs to see when the deploy phase of build/deploy process kicks in:

	```
	saw watch --raw rally-prod-webapp
	```

1. You will notice that AWS ECS will not be able to successfully start the Rally v2.1.0 web application but ECS will keep trying. (Meanwhile, Rally v2.0.1.11 will continue to run and serve the Rally website. ECS will not stop this v2.0.1.11 task until the v2.1.0 task starts successfully. This is what is meant by a blue-green deploy; there should be no downtime.)

1. Run the migration script on the production database as the *ec2-user*:

	```bash
	$ cd /home/ec2-user/migrate
	$ export CONFIG=/home/ec2-user/migrate/application-prod.properties
	$ ./run.sh
	```
		NOTE: The migration script should take about 3 minutes.

1. At some point during the migration process, Rally v2.1.0 will start successfully and ECS with roll the deploy to the other Rally v2.0.1.11 node, upgrading this node to Rally v2.1.0, as well.

1. Once both nodes are running Rally v2.1.0, perform sanity checks on the Rally v2.1.0 at https://rally.partners.org.

	