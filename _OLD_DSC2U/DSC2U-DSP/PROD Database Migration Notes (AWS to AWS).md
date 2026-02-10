
1. Connect to AnyConnect
2. SSH to prpweb1 and prpweb2
3. Shutdown DSC2U and SproutScribe (PROD) on both servers
	1. stop sprout service:  `sudo service sprout stop`
	2. stop dsc2u service: `sudo service dsc2u stop`
4. On your (scl30) computer, open a terminal app (e.g., iTerm) and navigate to `cd ~/tmp/dsc2u-sprout-test-db-migration/`
5. Export DSC2U database:

```bash
> export PGPASSWORD=`aws rds generate-db-auth-token --hostname rally.c2jt2a68uwxt.us-east-1.rds.amazonaws.com --port 5432 --region us-east-1 --username dsc2u-backup --profile dsc2u-backup | tr -d '\n'`
> time pg_dump -h localhost -p 15434 -Fc -U "dsc2u-backup" -n public --no-owner --no-privileges dsc2u > dsc2u-prod.sql
```

5. Export Sprout2pt0 database:

```bash
> export PGPASSWORD="$(aws rds generate-db-auth-token --hostname rally.c2jt2a68uwxt.us-east-1.rds.amazonaws.com --port 5432 --region us-east-1 --username sprout2pt0-backup --profile sprout2pt0-backup)"
> time pg_dump -h localhost -p 15434 -Fc -U "sprout2pt0-backup" -n public --no-owner --no-privileges --exclude-table=public.output_backup_20211216 sprout2pt0 > sprout2pt0-prod.sql
```

6. Restore Sprout2pt0 database:

```bash
> export PGPASSWORD=6386BF27-F318-49CA-AB3F-39E311DAF2B8
> pg_restore --dbname="sprout2pt0_prod" --schema=public --username="sprout2pt0_dba" --host=database-1.c3e0m2x2zq1s.us-east-1.rds.amazonaws.com --port=5432 -W sprout2pt0-prod.sql
```

6. Restore DSC2U database:

```bash
> export PGPASSWORD=7D4B4C58-247B-4624-AD34-2C5E5ED043BB
> pg_restore --dbname="dsc2u_prod" --schema=public --username="dsc2u_dba" --host=database-1.c3e0m2x2zq1s.us-east-1.rds.amazonaws.com --port=5432 -W dsc2u-prod.sql
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

## Perform in the SPROUT2PT0_PROD Database

This needs to be performed after to `pg-restore`:
```sql
GRANT SELECT ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_prod;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_prod;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_prod;
GRANT DELETE ON ALL TABLES IN SCHEMA public TO sprout2pt0_app_prod;

GRANT USAGE ON ALL SEQUENCES IN SCHEMA public to sprout2pt0_app_prod;
```

Start DSC2U and SproutScribe (PROD) on both servers
	1. start sprout service:  `sudo service sprout start`
	2. start dsc2u service: `sudo service dsc2u start`