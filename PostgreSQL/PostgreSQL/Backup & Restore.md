# PostgreSQL Backup & Restore

## To Backup a Specific Database

With no owner or privileges:

```
pg_dump -h lcs106.partners.org -p 15432 -Fc -U rally -n public --no-owner --no-privileges rally-test > rally-test-2_1_0.sql
```

With no owner (but include privileges);

```
pg_dump -h lcs106.partners.org -p 15432 -Fc -U rally -n public --no-owner --no-privileges rally-test > rally-test-v2-2-0-0.sql
```


## Clone Database on same Server

Run this only if the source database is being used (this will boot users off the database); not for PROD unless you really need to run this.

  

SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity

WHERE pg_stat_activity.datname = 'rally-load' AND pid <> pg_backend_pid();

  

The “clone” command:

  

create database "rally-123" with template "rally-load";

  

(source [https://stackoverflow.com/questions/876522/creating-a-copy-of-a-database-in-postgresql](https://stackoverflow.com/questions/876522/creating-a-copy-of-a-database-in-postgresql))

  

## Dump database

  

pg_dump -h rally.c2jt2a68uwxt.us-east-1.rds.amazonaws.com -Fc -o -U rally rally-load > rally-load.sql

## Restore database

  

There are several ways to dump and restore PostgreSQL databases. The method to use following the above pg_dump command is:

  

pg_restore -h localhost -p 5432 -U postgres -d rally-test rally-test.sql

  

This will restore to the “rally-test” database hosted on localhost, port 5432.

## Access rally-dev database from psql via stunnel

  

psql -h localhost -p 15432 -d rally-dev -U rally

  
  

## Copy database from localhost to AWS RDS (wiping out the target AWS database)

  

pg_dump --clean -h localhost -U postgres sprout2pt0 | psql -h lcs106.partners.org -p 15432 -U rally sprout2pt0-test

**