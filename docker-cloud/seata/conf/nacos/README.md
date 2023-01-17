
##nacos-config.sh
| 命令 | 描述        | 默认          |
|------|-----------|-------------|
|-h | nacos主机地址 | 127.0.0.1   |
|-p  | nacos主机端口 | 8848        |
|-g  | nacos分组名称 | public      |
|-t  | nacos命名空间 | DEFAULT_GROUP |
|-u  | nacos账户   | 无           |
|-w  | nacos密码   | 无           |

```bash
# nacos导入命令,把config.txt与nacos-config.sh传到服务器注意需要保持同级目录,然后运行下方命令
./nacos-config.sh -h xxx.xxx.xxx.xxx -g SEATA_GROUP -t dolphin-seata
```
