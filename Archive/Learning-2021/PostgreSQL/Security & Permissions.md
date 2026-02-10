# PostgreSQL Security & Permissions

## To grant 'all' to user

```SQL
grant all on broadcast_email_history to pnp1;  
```

## List Roles for all Users

```SQL
SELECT r.rolname, r.rolsuper, r.rolinherit,  
 r.rolcreaterole, r.rolcreatedb, r.rolcanlogin,  
 r.rolconnlimit, r.rolvaliduntil,  
 ARRAY(SELECT b.rolname  
 FROM pg_catalog.pg_auth_members m  
 JOIN pg_catalog.pg_roles b ON (m.roleid = b.oid)  
 WHERE m.member = r.oid) as memberof  
, r.rolreplication  
, r.rolbypassrls  
FROM pg_catalog.pg_roles r  
WHERE r.rolname !~ '^pg_'  
ORDER BY 1;
```

##### Sample output

| rolname | rolsuper | rolinherit | rolcreaterole | rolcreatedb | rolcanlogin | rolconnlimit | rolvaliduntil | memberof | rolreplication | rolbypassrls |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| dsc2u | false | true | false | false | true | -1 | NULL | {dsc2u\_readwrite\_test,rds\_iam} | false | false |
| dsc2u-backup | false | true | false | false | true | -1 | NULL | {rds\_iam,dsc2u\_readonly\_prod} | false | false |
| dsc2u\_readonly\_prod | false | true | false | false | false | -1 | NULL |  | false | false |


## Show Access to a particular Table

```SQL
SELECT grantee, privilege_type  
FROM information_schema.role_table_grants  
WHERE table_name='broadcast_email_history';
```

##### Sample output

| grantee | privilege\_type |
| :--- | :--- |
| pnp1 | INSERT |
| pnp1 | SELECT |
| pnp1 | UPDATE |
| pnp1 | DELETE |
| pnp1 | TRUNCATE |
| pnp1 | REFERENCES |
| pnp1 | TRIGGER |
| xz020 | INSERT |
| xz020 | SELECT |
| xz020 | UPDATE |

## Show Access to all Sequences

```SQL
SELECT relname, relacl  
FROM pg_class  
WHERE relkind = 'S'  
 AND relacl is not null  
 AND relnamespace IN (  
 SELECT oid  
 FROM pg_namespace  
 WHERE nspname NOT LIKE 'pg_%'  
 AND nspname != 'information_schema'  
);
```

##### Sample output

| relname | relacl |
| :--- | :--- |
| affiliate\_membership\_id\_seq | {dsc2u=rwU/dsc2u,dsc2u\_readwrite\_prod=U/dsc2u,dsc2u\_readonly\_prod=rU/dsc2u} |
| audit\_log\_id\_seq | {dsc2u=rwU/dsc2u,dsc2u\_readwrite\_prod=U/dsc2u,dsc2u\_readonly\_prod=rU/dsc2u} |
| cost\_matrix\_id\_seq | {dsc2u=rwU/dsc2u,dsc2u\_readwrite\_prod=U/dsc2u,dsc2u\_readonly\_prod=r/dsc2u} |
| country\_id\_seq | {dsc2u=rwU/dsc2u,dsc2u\_readwrite\_prod=U/dsc2u,dsc2u\_readonly\_prod=rU/dsc2u} |
| coupon\_cart\_id\_seq | {dsc2u=rwU/dsc2u,dsc2u\_readwrite\_prod=U/dsc2u,dsc2u\_readonly\_prod=rU/dsc2u} |
| coupon\_id\_seq | {dsc2u=rwU/dsc2u,dsc2u\_readwrite\_prod=U/dsc2u,dsc2u\_readonly\_prod=rU/dsc2u} |

## Grant Select to all Sequences in Schema

```SQL
GRANT SELECT ON ALL SEQUENCES IN SCHEMA public TO dsc2u_readonly_prod;
```


## List Ownership of all Database Objects

```sql
SELECT  
    nspname AS schema,  
    relname AS object_name,  
    pg_catalog.pg_get_userbyid(relowner) AS owner  
FROM  
    pg_catalog.pg_class c  
    LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace  
WHERE  
    nspname NOT LIKE 'pg_%'  
    AND nspname != 'information_schema'  
    AND relkind IN ('r','v','m','S','f','')  
ORDER BY  
    nspname,  
    relname;
```

## Reassign Ownership (finicky)

You might be able to reassign ownership of all database objects this way, but I was only able to do this with one database; the rest I needed to use a more manual approach. Note, before the below statement can work the user you are logged in as must be a member of both the source and destination role.

```sql
REASSIGN OWNED BY root to "rally-dba";
```

## Manually Changing Ownership of Database Objects

First run the following statements to generate `alter` statements for tables, sequences, views, and functions.

#### Tables
```sql
SELECT 'ALTER TABLE '|| schemaname || '."' || tablename ||'" OWNER TO "rally-dba";'  
FROM pg_tables WHERE NOT schemaname IN ('pg_catalog', 'information_schema')  
ORDER BY schemaname, tablename;
```

#### Sequences
```sql
SELECT 'ALTER SEQUENCE '|| sequence_schema || '."' || sequence_name ||'" OWNER TO "rally-dba";'  
FROM information_schema.sequences WHERE NOT sequence_schema IN ('pg_catalog', 'information_schema')  
ORDER BY sequence_schema, sequence_name;
```

#### Views
```sql
SELECT 'ALTER VIEW '|| table_schema || '."' || table_name ||'" OWNER TO my_new_owner;'  
FROM information_schema.views WHERE NOT table_schema IN ('pg_catalog', 'information_schema')  
ORDER BY table_schema, table_name;
```

#### Functions
```sql
SELECT 'ALTER FUNCTION "'||nsp.nspname||'"."'||p.proname||'"('||pg_get_function_identity_arguments(p.oid)||') ' ||  
       'OWNER TO ' || '"rally-dba"' || ';' AS a FROM pg_proc p JOIN pg_namespace nsp ON p.pronamespace = nsp.oid  
WHERE NOT nsp.nspname IN ('pg_catalog', 'information_schema');
```

#### List all database objects (tables, views, functions) and their permissions and ownership

```SQL
SELECT rug.grantor,
        rug.grantee,
        rug.object_catalog,
        rug.object_schema,
        rug.object_name,
        rug.object_type,
        rug.privilege_type,
        rug.is_grantable,
        null::text AS with_hierarchy
    FROM information_schema.role_usage_grants rug
    WHERE rug.object_schema NOT IN ( 'pg_catalog', 'information_schema' )
        AND grantor <> grantee
UNION
SELECT rtg.grantor,
        rtg.grantee,
        rtg.table_catalog,
        rtg.table_schema,
        rtg.table_name,
        tab.table_type,
        rtg.privilege_type,
        rtg.is_grantable,
        rtg.with_hierarchy
    FROM information_schema.role_table_grants rtg
    LEFT JOIN information_schema.tables tab
        ON ( tab.table_catalog = rtg.table_catalog
            AND tab.table_schema = rtg.table_schema
            AND tab.table_name = rtg.table_name )
    WHERE rtg.table_schema NOT IN ( 'pg_catalog', 'information_schema' )
        AND grantor <> grantee
UNION
SELECT rrg.grantor,
        rrg.grantee,
        rrg.routine_catalog,
        rrg.routine_schema,
        rrg.routine_name,
        fcn.routine_type,
        rrg.privilege_type,
        rrg.is_grantable,
        null::text AS with_hierarchy
    FROM information_schema.role_routine_grants rrg
    LEFT JOIN information_schema.routines fcn
        ON ( fcn.routine_catalog = rrg.routine_catalog
            AND fcn.routine_schema = rrg.routine_schema
            AND fcn.routine_name = rrg.routine_name )
    WHERE rrg.specific_schema NOT IN ( 'pg_catalog', 'information_schema' )
        AND grantor <> grantee
UNION
SELECT rug.grantor,
        rug.grantee,
        rug.udt_catalog,
        rug.udt_schema,
        rug.udt_name,
        ''::text AS udt_type,
        rug.privilege_type,
        rug.is_grantable,
        null::text AS with_hierarchy
    FROM information_schema.role_udt_grants rug
    WHERE rug.udt_schema NOT IN ( 'pg_catalog', 'information_schema' )
        AND substr ( rug.udt_schema, 1, 3 ) <> 'pg_'
        AND grantor <> grantee 
	order by 2;
```