
### To find which process is using a port:

```bash
sudo lsof -i -P | grep LISTEN | grep :$PORT
```

