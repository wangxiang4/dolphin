
```bash
# upsource容器中使用的用户和组不是13001：13001,而是其他,也许是码头工人的用户,需要手动授权,或者写个Dockerfile在里面授权
chmod -R 777 /usr/software/dockerDatabase/upsource/data
chmod -R 777 /usr/software/dockerDatabase/upsource/conf
chmod -R 777 /usr/software/dockerDatabase/upsource/logs
chmod -R 777 /usr/software/dockerDatabase/upsource/backups
```
