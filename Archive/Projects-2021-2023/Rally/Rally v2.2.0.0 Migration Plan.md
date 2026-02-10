
1. ~~Messaging/Banner on website about downtime?
2. ~~Enable WAF for old Rally (20:00)
3. ~~Shutdown old Rally website (20:00)
5. ~~Run migration script (25 minutes) (20:05)
6. ~~Start rally-prod-service-fargate (https://us-east-1.console.aws.amazon.com/ecs/v2/clusters/rally-prod-cluster/services/rally-prod-service-fargate/update?region=us-east-1)
7. ~~Update prod-fargate/application.properties (done at 18:11) (20:10)~~

```properties
rally.saved.search.polling = true
rally.base.url=https://rally.massgeneralbrigham.org
```
6. ~~Run `updateProdFarateProperties.sh` script (20:11)~~
7. ~~Restart `rally-prod-service-fargate` service (20:12)
8. ~~Point Route53 DNS entries from `rally-prod-application-1721326692.us-east-1.elb.amazonaws.com` to `rally-prod-lb-856363326.us-east-1.elb.amazonaws.com` here https://us-east-1.console.aws.amazon.com/route53/v2/hostedzones?region=us-east-1#ListRecordSets/ZTEL1MLODQ7SI
	![[Screenshot 2023-11-18 at 20.38.34.png]]
9. After spot testing is complete, turn off WAF