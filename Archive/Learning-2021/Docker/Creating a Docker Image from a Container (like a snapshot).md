# How to create a Docker Image from a Container

This process is useful if you want to create snapshot of a Docker container for later use. The project I am working on now requires a PostgreSQL container to be used for testing the Research Ally project.

## Step 1: Create a Base Container

First download the base image. The `-e PGDATA=/var/lib/postgresql/pgdata` environment variable tells the container to store the database data *inside* the container. This is not recommended for production but perfect for our testing use case.

```
docker create --name pg_testing_ra_2-2-0 -p 5432:5432 -e PGDATA=/var/lib/postgresql/pgdata -e POSTGRES_PASSWORD=secret postgres:10.19-alpine
```

Start a container with this image:

```
docker start pg_testing_ra_2-2-0
```

Log in to the Postgres instance:

```
psql -h localhost -p 25432 -U postgres
```

Create a database:

```
create database qa;
```

You will need to register the `uuid-ossp` extension for the uuid import to function:

```
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
```

Import database from real Rally:

```
pg_restore -h localhost -p 25432 -U postgres -d qa rallytest.dump
```

or, if the export is non-binary:

```
psql -h localhost -p 25432 -U postgres -d qa < rallytest.dump
```


Save snapshot (image) of Rally DB container. The first parameter is the container name; the second is the image tag:

```
docker commit pg_testing_ra_2-2-0 pg_testing_ra_2-2-0
```

