

Run these commands on my (scl30) local machine in the `/Users/scl30/backup/data/dsc2u-aws-2-on-prem` directory.

## Secret Stuff
```properties
lcsm070=9D9DDD12-1B4A-4529-8FC8-142D14E8DA1F
lcsn157=B26F774A-B802-4151-98AD-62B315739FBE (no longer used after mirroring)
```


## Create  new databases
```sql
create database "dsc2u_prod";
create database "dsc2u_test";
create database "sprout2pt0_prod";
create database "sprout2pt0_test";

create user "dsc2u_dba";
create user "sprout2pt0_dba";

create user "dsc2u_app_prod";
create user "dsc2u_app_test";
create user "sprout2pt0_app_prod";
create user "sprout2pt0_app_test";

alter database "dsc2u_prod" owner to "dsc2u_dba";
alter database "dsc2u_test" owner to "dsc2u_dba";
alter database "sprout2pt0_prod" owner to "sprout2pt0_dba";
alter database "sprout2pt0_test" owner to "sprout2pt0_dba";

grant all privileges ON database dsc2u_prod to dsc2u_dba;
grant all privileges ON database dsc2u_test to dsc2u_dba;
grant all privileges ON database sprout2pt0_prod to sprout2pt0_dba;
grant all privileges ON database sprout2pt0_test to sprout2pt0_dba;

ALTER ROLE "dsc2u_dba" WITH LOGIN;
ALTER ROLE "sprout2pt0_dba" WITH LOGIN;

ALTER ROLE "dsc2u_app_prod" WITH LOGIN;
ALTER ROLE "dsc2u_app_test" WITH LOGIN;
ALTER ROLE "sprout2pt0_app_prod" WITH LOGIN;
ALTER ROLE "sprout2pt0_app_test" WITH LOGIN;

ALTER USER "dsc2u_dba" WITH PASSWORD '7D4B4C58-247B-4624-AD34-2C5E5ED043BB';
ALTER USER "sprout2pt0_dba" WITH PASSWORD '6386BF27-F318-49CA-AB3F-39E311DAF2B8';

ALTER USER "dsc2u_app_prod" WITH PASSWORD '60527747-735C-4E17-A2A2-C1CF5E8C8A35';
ALTER USER "dsc2u_app_test" WITH PASSWORD '21FF89F6-357F-491C-8A1B-9A7C8902E3C9';
ALTER USER "sprout2pt0_app_prod" WITH PASSWORD '1214BC8E-A5D1-4FDE-B87E-B2917F55E2F3';
ALTER USER "sprout2pt0_app_test" WITH PASSWORD 'A7AED7A1-E631-45E8-8BD2-9837211B5951';

```


## Perform in the DSC2U_PROD Database

This needs to be performed prior to `pg-restore`:
```sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";  
CREATE EXTENSION IF NOT EXISTS "pg_trgm";  
CREATE EXTENSION IF NOT EXISTS "unaccent";
```


## Perform in the DSC2U_TEST Database

This needs to be performed prior to `pg-restore`:
```sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";  
CREATE EXTENSION IF NOT EXISTS "pg_trgm";  
CREATE EXTENSION IF NOT EXISTS "unaccent";
```



## Backup/Restore DSC2U_PROD Database
```bash
> export PGPASSWORD=`aws rds generate-db-auth-token --hostname rally.c2jt2a68uwxt.us-east-1.rds.amazonaws.com --port 5432 --region us-east-1 --username dsc2u-backup --profile dsc2u-backup | tr -d '\n'`

> pg_dump -h localhost -p 15434 -Fc -U "dsc2u-backup" -n public --no-owner --no-privileges dsc2u > dsc2u-prod.sql

-- Login as the postgres superuser, connect to the dsc2u_prod database and run:

> GRANT ALL ON schema public TO dsc2u_dba;

> pg_restore --dbname="dsc2u_prod" --schema=public --username="dsc2u_dba" --host=lcsm070.partners.org --port=5432 -W dsc2u-prod.sql
```

## Backup/Restore DSC2U_TEST Database
```bash
> pg_dump -h localhost -p 15434 -Fc -U "rally" -W -n public --no-owner --no-privileges "dsc2u-test" > dsc2u-test.sql

> pg_restore --dbname="dsc2u_test" --schema=public --username="dsc2u_dba" --host=lcsm070.partners.org --port=5432 -W dsc2u-test.sql
```

## Backup/Restore Sprout2pt0_PROD Database
```bash
> export PGPASSWORD=`aws rds generate-db-auth-token --hostname rally.c2jt2a68uwxt.us-east-1.rds.amazonaws.com --port 5432 --region us-east-1 --username sprout2pt0-backup --profile sprout2pt0-backup | tr -d '\n'`

> pg_dump -h localhost -p 15434 -Fc -U "sprout2pt0-backup" -n public --no-owner --no-privileges --exclude-table=public.output_backup_20211216 sprout2pt0 > sprout2pt0-prod.sql

-- Login as the postgres superuser, connect to the sprout2pt0_prod database and run:

> GRANT ALL ON schema public TO sprout2pt0_dba;

$ pg_restore --dbname="sprout2pt0_prod" --schema=public --username="sprout2pt0_dba" --host=lcsm070.partners.org --port=5435 -W sprout2pt0-prod.sql
```

## Backup/Restore Sprout2pt0_TEST Database
```bash
> pg_dump -h localhost -p 15434 -Fc -U "rally" -W -n public --no-owner --no-privileges --exclude-table=public.output_backup_20211216 "sprout2pt0-test" > sprout2pt0-test.sql

> pg_restore --dbname="sprout2pt0_test" --schema=public --username="sprout2pt0_dba" --host=lcsm070.partners.org --port=5432 -W sprout2pt0-test.sql
```




## Perform in the DSC2U_PROD Database

This needs to be performed after to `pg-restore`:
```sql
GRANT SELECT ON ALL TABLES IN SCHEMA public TO dsc2u_app_prod;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO dsc2u_app_prod;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO dsc2u_app_prod;
GRANT DELETE ON ALL TABLES IN SCHEMA public TO dsc2u_app_prod;

GRANT USAGE ON ALL SEQUENCES IN SCHEMA public to dsc2u_app_prod;
```


## Perform in the DSC2U_TEST Database

This needs to be performed after to `pg-restore`:
```sql
GRANT SELECT ON ALL TABLES IN SCHEMA public TO dsc2u_app_test;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO dsc2u_app_test;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO dsc2u_app_test;
GRANT DELETE ON ALL TABLES IN SCHEMA public TO dsc2u_app_test;

GRANT USAGE ON ALL SEQUENCES IN SCHEMA public to dsc2u_app_test;
```


## Perform in the SPROUT2PT0_PROD Database

This needs to be performed after to `pg-restore`:
```sql
GRANT SELECT ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_prod;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_prod;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_prod;
GRANT DELETE ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_prod;

GRANT USAGE ON ALL SEQUENCES IN SCHEMA public to sprout2pt0_app_prod;
```

## Perform in the SPROUT2PT0_TEST Database

This needs to be performed after to `pg-restore`:
```sql
GRANT SELECT ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_test;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_test;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_test;
GRANT DELETE ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_test;

GRANT USAGE ON ALL SEQUENCES IN SCHEMA public to sprout2pt0_app_test;
```








## Legacy Stuff

9D9DDD12-1B4A-4529-8FC8-142D14E8DA1F
B26F774A-B802-4151-98AD-62B315739FBE
6D1668DD-923F-43E1-A3A1-513F2E2FDA59

