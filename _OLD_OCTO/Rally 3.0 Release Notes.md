
- Run `core/sql/v3.0.0.0/v3.0.0.0.sql`
	- first, check to find breaking changes in the database that might cause v2.2.5.3 to break
		- if we find any, then we will need down time
- update application.properties for PROD in S3
	- enable PI email notifications
	- point to correct CMS/SAM branch
- Manually upgrade PROD Fargate to v3.0.0.0 branch
	- question: has the serialization model changed in any way that will break when deployed?