# KnowHow Setup Notes

After a new server build, run the following commands to configure the KnowHow server:

### ktutil
Type the `ktutil` command and enter the following when prompted:

```shell
> addent -password -p scl30@PARTNERS.ORG -k 1 -e rc4-hmac
> wkt scl30.keytab
```

This will create a Kerberos keytab file which will be used the following command.

### kinit
Type the following command:

```shell
> kinit -k -t /home/scl30/scl30.keytab scl30@PARTNERS.ORG
```

Once this is complete you can install the Microsoft SQL Tools requirements:

### Run yum (install dependencies)
Run the following command to recreate a reference to the Microsoft yum repo:

```shell
> curl https://packages.microsoft.com/config/rhel/8/prod.repo > /etc/yum.repos.d/msprod.repo
```

Run the following command to remove any old/legacy references to odbc dependencies. (This is not likely to need to remove anything from a pristene system.):

```shell
> sudo yum remove mssql-tools unixODBC-utf16-devel
```

Run the following command to install required packages:

```shell
> sudo yum install mssql-tools unixODBC-deve
```

The bcp (batch copy command) and sqlcmd executables should now be installed.

### Create Symbolic Links
By default the Microsoft SQL Tools are installed in the `/opt/mssql-tools/bin/` directory. Create symbolic links to these executables so they can be run more easily from anywhere on the system:

```shell
> ln -s /opt/mssql-tools/bin/bcp /usr/bin/bcp
> ln -s /opt/mssql-tools/bin/sqlcmd /usr/bin/sqlcmd
```