# PostgreSQL Tips

## Change Timezone of Date

Add ` at time zone '-4'` to any `Date` or `Timestamp` field type, like this:

```SQL
SELECT localtimestamp at time zone '-4';
```

## Switch Database
```sql
\c [database_name]
```

## Alternate Results Output
```sql
\x on
```

## Temporarily disable access to database

```
ALTER DATABASE "rally-test" ALLOW_CONNECTIONS false;
```

## Reenable access to database

```
ALTER DATABASE "rally-test" ALLOW_CONNECTIONS true;
```