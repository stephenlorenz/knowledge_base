- [ ] Migrate DEV and TEST database to new small instance
- [ ] Migrate PROD database to new Aurora instance/cluster

### Note!!!
Before any pg_restore process is run, make sure the target database has the appropriate extensions, or the restore will fail:
```sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";  
CREATE EXTENSION IF NOT EXISTS "pg_trgm";  
CREATE EXTENSION IF NOT EXISTS "unaccent";
CREATE EXTENSION IF NOT EXISTS "plpgsql";
CREATE EXTENSION IF NOT EXISTS "aws_commons";
CREATE EXTENSION IF NOT EXISTS "aws_s3";
```

## Rally TEST and TEST-DEV Database Migration Scripts

### Actual rally-test-dev database backup and restore scripts
```bash
$ pg_dump -h localhost -p 15434 -Fc -U rally -n public --no-owner --no-privileges rally-test-dev > rally-test-dev-v2-2-0-0.sql
$ export PGPASSWORD=`aws rds generate-db-auth-token --hostname rally-dev-test-db.c2jt2a68uwxt.us-east-1.rds.amazonaws.com --port 5432 --region us-east-1 --username rally-dba --profile rally-dba | tr -d '\n'`
$ pg_restore --dbname=rally-dev --schema=public --username=rally-dba --host=localhost --port=15435 rally-test-dev-v2-2-0-0.sql
```
### Actual rally-test database backup and restore scripts
```bash
$ pg_dump -h localhost -p 15434 -Fc -U rally -n public --no-owner --no-privileges rally-test > rally-test-v2-2-0-0.sql
$ export PGPASSWORD=`aws rds generate-db-auth-token --hostname rally-dev-test-db.c2jt2a68uwxt.us-east-1.rds.amazonaws.com --port 5432 --region us-east-1 --username rally-dba --profile rally-dba | tr -d '\n'`
$ pg_restore --dbname=rally-test --schema=public --username=rally-dba --host=localhost --port=15435 rally-test-v2-2-0-0.sql
```

### To migrate other schemas (for example)
```
$ pg_dump -h localhost -p 15434 -Fc -U rally -n '"UMLS_MedlinePlus_2021AB"' --no-owner --no-privileges rally-test > rally-test-umls-medlineplus-2021AB.dump

$ pg_restore --dbname=rally-test --schema=UMLS_MedlinePlus_2021AB --username=rally-dba --host=localhost --port=15435 rally-test-umls-medlineplus-2021AB.dump
```

## Post Database Migration Steps

#### Run the Following in the DEV database
```sql
GRANT SELECT ON ALL TABLES IN SCHEMA public TO rally_readonly_dev;

GRANT SELECT ON ALL TABLES IN SCHEMA public TO rally_readwrite_dev;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO rally_readwrite_dev;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO rally_readwrite_dev;
GRANT DELETE ON ALL TABLES IN SCHEMA public TO rally_readwrite_dev;

GRANT USAGE ON ALL SEQUENCES IN SCHEMA public to rally_readwrite_dev;

-- The following 2 statements are necessary to alter default privileges
GRANT rally_readonly_dev TO "rally-dba";
GRANT rally_readwrite_dev TO "rally-dba";

ALTER ROLE "rally_app_dev" WITH LOGIN;
ALTER ROLE mlw30 WITH LOGIN;
ALTER ROLE pnp1 WITH LOGIN;
ALTER ROLE gae WITH LOGIN;
ALTER ROLE hmp13 WITH LOGIN;
ALTER ROLE jc713 WITH LOGIN;
ALTER ROLE ldc20 WITH LOGIN;

GRANT rds_iam TO rally_app_dev;
GRANT rally_readwrite_dev to rally_app_dev;
GRANT rally_readwrite_dev to mlw30;
GRANT rally_readwrite_dev to pnp1;
GRANT rally_readonly_dev to gae;
GRANT rally_readonly_dev to hmp13;
GRANT rally_readonly_dev to jc713;
GRANT rally_readonly_dev to ldc20;

ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readonly_dev
IN SCHEMA public
GRANT SELECT ON TABLES 
TO rally_readonly_dev;

ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readwrite_dev
IN SCHEMA public
GRANT SELECT ON TABLES 
TO rally_readwrite_dev;

ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readwrite_dev
IN SCHEMA public
GRANT INSERT ON TABLES 
TO rally_readwrite_dev;

ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readwrite_dev
IN SCHEMA public
GRANT UPDATE ON TABLES 
TO rally_readwrite_dev;

ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readwrite_dev
IN SCHEMA public
GRANT DELETE ON TABLES 
TO rally_readwrite_dev;

ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readwrite_dev
IN SCHEMA public
GRANT USAGE ON SEQUENCES 
TO rally_readwrite_dev;

```



#### Run the Following in the TEST database
```sql
GRANT SELECT ON ALL TABLES IN SCHEMA public TO rally_readonly_test;

GRANT SELECT ON ALL TABLES IN SCHEMA public TO rally_readwrite_test;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO rally_readwrite_test;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO rally_readwrite_test;
GRANT DELETE ON ALL TABLES IN SCHEMA public TO rally_readwrite_test;

GRANT USAGE ON ALL SEQUENCES IN SCHEMA public to rally_readwrite_test;

-- The following 2 statements are necessary to alter default privileges
GRANT rally_readonly_test TO "rally-dba";
GRANT rally_readwrite_test TO "rally-dba";

ALTER ROLE "rally_app_test" WITH LOGIN;
GRANT rds_iam TO rally_app_test;
GRANT rally_readwrite_test to rally_app_test;
GRANT rally_readwrite_test to pnp1;
GRANT rally_readonly_test to mlw30;
GRANT rally_readonly_test to gae;
GRANT rally_readonly_test to hmp13;
GRANT rally_readonly_test to jc713;
GRANT rally_readonly_test to ldc20;


ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readonly_test
IN SCHEMA public
GRANT SELECT ON TABLES 
TO rally_readonly_test;

ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readwrite_test
IN SCHEMA public
GRANT SELECT ON TABLES 
TO rally_readwrite_test;

ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readwrite_test
IN SCHEMA public
GRANT INSERT ON TABLES 
TO rally_readwrite_test;

ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readwrite_test
IN SCHEMA public
GRANT UPDATE ON TABLES 
TO rally_readwrite_test;

ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readwrite_test
IN SCHEMA public
GRANT DELETE ON TABLES 
TO rally_readwrite_test;

ALTER DEFAULT PRIVILEGES
FOR ROLE rally_readwrite_test
IN SCHEMA public
GRANT USAGE ON SEQUENCES 
TO rally_readwrite_test;

```



## Miscellaneous Notes

These notes are legacy artifacts which might or might not contain a useful historical record.


```SQL
grant rds_superuser to rally;  
  
-- drop database "rally-test";  
-- create database "rally-test";  
  
-- drop database "rally-test-dev";  
-- create database "rally-test-dev";  
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";  
CREATE EXTENSION IF NOT EXISTS "pg_trgm";  
  
select * from pg_stat_activity;  
  
select pg_terminate_backend(pid)  
from pg_stat_activity  
where pid = '12253';  
  
drop role scl30;  
  
CREATE USER scl30 WITH  
 LOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION;  
  
GRANT rds_iam TO scl30;  
  
GRANT rally_readwrite_dev TO scl30;  
GRANT rally_readwrite_test TO scl30;  
  
create role ygh;  
create role gae;  
create role hmp13;  
create role jc713;  
create role scl30;  
create role rally_app_dev;  
create role pnp1;  
create role xz020;  
create role mlw30;  
create role rally_users_schema_test;  
create role medlineplus_upgrade;  
create role rally_readonly_dev;  
create role rally_readonly_test;  
create role rally_readwrite_dev;  
create role rally_readwrite_test;  
create role ldc20;  
create role rally_app_test;  
create role rally_readwrite_test_dev;  
```

  
  
select count(*) from users;


### Backup test database
```
pg_dump -h lcs106.partners.org -p 15432 -Fc -U rally -n public --no-owner --no-privileges rally-test > rally-test-v2-2-0-0.sql
```

or (for scl30)

```
pg_dump -h localhost -p 15434 -Fc -U rally -n public --no-owner --no-privileges rally-test > rally-test-v2-2-0-0.sql
```

### Restore test database
```
pg_restore -h rally-dev-test-db.c2jt2a68uwxt.us-east-1.rds.amazonaws.com -p 5432 -U postgres -d rally-test rally-test-v2-2-0-0.sql
```

or (for scl30)

```
pg_restore --dbname=rally-test --schema=public --username=root --host=localhost --port=15435 rally-test-v2-2-0-0.sql
```


### Backup test-dev database
```
pg_dump -h lcs106.partners.org -p 15432 -Fc -U rally -n public --no-owner --no-privileges rally-test-dev > rally-test-dev-v2-2-0-0.sql
```

### Restore test-dev database
```
pg_restore -h rally-dev-test-db.c2jt2a68uwxt.us-east-1.rds.amazonaws.com -p 5432 -U postgres -d rally-test-dev rally-test-dev-v2-2-0-0.sql
```

