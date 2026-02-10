
#### Before migration, ssh to lcsproxy:

```bash
> cd /etc/httpd/conf.d
> cp ssl.conf.down ssl.conf
> service httpd reload
```

#### During migration (tail end):

Ssh to lcs123 and lcsn150:

```bash
> sudo su
> cd /opt/jboss/jboss-as-7.1.2.Final/domain/configuration
> cp domain.xml domain.xml.20240405
> cp domain.xml.with-failover domain.xml
> service jboss restart (or stop/start)
```

#### After migration, ssh to lcsproxy:

```bash
> cd /etc/httpd/conf.d
> cp ssl.conf.up ssl.conf
> service httpd reload
> setenforce 1
```
