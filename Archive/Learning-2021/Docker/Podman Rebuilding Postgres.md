# Podman 

```
podman machine init --cpus 1 --memory 2048 --disk-size 30
```

```
podman machine start
```

```
docker run --name postgres -p 5432:5432 -e POSTGRES_USER=migrate -e POSTGRES_PASSWORD=secret -d postgres
```

