### Starting a container in debug mode (at the bash prompt)

One way to start a container in debug mode is using the command below:

```bash
docker run -it --rm --entrypoint bash {container-id}
```

Source: https://medium.com/@supritshah1289/debugging-docker-container-that-wont-start-af429adbb568