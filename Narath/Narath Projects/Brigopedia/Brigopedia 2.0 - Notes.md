 ### Server Settings

| Field | Value |
|---|---|
| Server Name  | rc-brigopedia.partners.org  |
| Server IP  | 172.31.206.57  |  

### Old Database connection settings

| Field | Value |
|---|---|
| Server Name  | mysql4.research.partners.org  |
| Database Type  | MySQL  |  
| Database Schema Name | brigopedia2020  |  
| Database Username | brigopedia |
| Database Password | Br1GjtQxC8DRUMdat |

### New Database connection settings

| Field | Value |
|---|---|
| Server Name  | mysql4.research.partners.org  |
| Database Type  | MySQL  |  
| Database Schema Name | brigopedia2022  |  
| Database Username | brigopedia |
| Database Password | Br1GjtQxC8DRUMdat |

### Migrate database

Use this single command to migrate the database from the `rc-brigopedia` `/home/scl30/migrate` directory

```bash
> /home/scl30/migrate/migrateDb.sh
```

### Move testing Mediawiki 1.37.1 installation

``` Shell
> mv /var/www/wiki /var/www/wiki202203??
```

### Restore the clean Mediawiki 1.37.1 installation

``` Shell
> cp -R /var/www/wikiOrig /var/www/wiki
```

### Manually Run Mediawiki Upgrade
Visit https://rc-brigopedia.partners.org/ and step through the wizard.

### Copy files from staging backup directory
```Shell
> cp -nR /home/scl30/restore/wiki/skins /var/www/wiki
> cp -nR /home/scl30/restore/wiki/extensions /var/www/wiki
> cp -nR /home/scl30/restore/wiki/vendor /var/www/wiki
> cp -nR /home/scl30/restore/wiki/includes /var/www/wiki
> cp /home/scl30/restore/wiki/logo.png /var/www/wiki
> cp /home/scl30/restore/wiki/LocalSettings.php /var/www/wiki
> cp /home/scl30/restore/wiki/GetSchedule.php /var/www/wiki/
```

### Copy files from old brigopedia server
```Shell
> sudo chmod -R 777 /var/www/wiki
> rsync -avzh -O --no-perms --ignore-existing scl30@brigopedia.partners.org:/var/www/mediawiki/images /var/www/wiki
> sudo chmod -R 775 /var/www/wiki
```

### Post Migration Manual Fixes:

After the migration you will need to perform several manual fixes.

##### Section Editing Auto-Edit Links
By default Mediawiki will add "Section editing" tags to the end of each section heading and to the auto-generated table of contents (TOC). To disable this, edit each page where this is an issue and added the line:

```markdown
__NOEDITSECTION__
```

____

### Add cron job to backup database and content
```crontab
0 0 * * * /opt/backup/scripts/backup
* * * * * /opt/mediawiki/scripts/updatePermissions.sh
```