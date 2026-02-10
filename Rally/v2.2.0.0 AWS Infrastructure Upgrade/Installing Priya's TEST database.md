
First download the init.sql script from s3:

```bash
aws s3 cp s3://rally-database/init.sql .
```

Export the temporary IAM password token:

```bash
export PGPASSWORD=`aws rds generate-db-auth-token --hostname rally-dev-test-db.c2jt2a68uwxt.us-east-1.rds.amazonaws.com --port 5432 --region us-east-1 --username rally-dba --profile rally-dba | tr -d '\n'`
```

Login to `rally-test` database:

```SQL
drop database "rally-test-lite";
```

Run the following SQL commands:

```SQL
create database "rally-test-lite";
```

Run the import:

```bash
psql --dbname=rally-test-lite --username=rally-dba --host=localhost --port=15435 < init.sql
```

Post import:

```SQL
\c "rally-test-lite"
GRANT SELECT ON ALL TABLES IN SCHEMA public TO rally_readonly_dev;

GRANT SELECT ON ALL TABLES IN SCHEMA public TO rally_readwrite_dev;
GRANT INSERT ON ALL TABLES IN SCHEMA public TO rally_readwrite_dev;
GRANT UPDATE ON ALL TABLES IN SCHEMA public TO rally_readwrite_dev;
GRANT DELETE ON ALL TABLES IN SCHEMA public TO rally_readwrite_dev;

GRANT USAGE ON ALL SEQUENCES IN SCHEMA public to rally_readwrite_dev;

GRANT USAGE ON SCHEMA public TO rally_readwrite_dev;

```