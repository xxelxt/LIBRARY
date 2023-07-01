NOTE WHEN CLONING: THE PROJECT ROOT DIRECTORY MUST BE NAMED "LIBRARY"
```
└📁 LIBRARY
  └📁 gradle
  └📁 lib
  ...
```

You must add a file named `user.txt` in `lib/src/main/java/library/mysql/user.txt` with your username and password for mysql database:
```
root
password
```


Open terminal here: `C:\Program Files\MySQL\MySQL Server 8.0\bin`

Import MySQL:

```
mysqldump -u root -p library < dump.sql
```

Export MySQL:

```
mysqldump -u root -p library > dump.sql
```
