### Dump source database

```shell
pg_dumpall -h localhost -U postgres -f localPostgreSQL.dump
```

This command might repeatly ask for a password. You can avoid that by creating a ~/.pgpass file, https://www.postgresql.org/docs/9.4/libpq-pgpass.html.

### Load destination database

Note, you might want to create a temporary user, e.g. `migrate`, to run the import. Otherwise, the import might conflict with a user referenced in the dump file and cause the import to fail.

```shell
psql -h 127.0.0.1 -p 5432 -U migrate -f localPostgreSQL.dump
```