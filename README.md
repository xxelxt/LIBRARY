NOTE WHEN CLONING: THE PROJECT ROOT DIRECTORY MUST BE NAMED "LIBRARY"
```
└📁 LIBRARY
  └📁 gradle
  └📁 lib
  ...
```

Needed files:

`user.txt` in `lib/src/main/java/library/mysql/user.txt` with your username and password for MySQL database:
```
root
password
```

`cloudinary_config.txt` in `lib/src/main/java/library/application/util/cloudinary_config.txt` with your Cloudinary credentials:
```
cloud_name=your_cloud_name
api_key=your_api_key
api_secret=your_api_secret
secure=true
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
