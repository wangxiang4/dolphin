
```bash
# 进入容器
docker exec -it -u root [id] bin/bash

# 将文件复制到容器
docker cp 源文件位置 容器ID:/var/jenkins_home/tools/apache-maven-3.9.0

# 使用root用户进入容器进行授权
docker exec -it -u root 容器ID bin/bash
chmod -R 777 /var/jenkins_home/tools

# 更换jenkins国内源
tee /var/jenkins_home/hudson.model.UpdateCenter.xml <<-'EOF'
<?xml version='1.1' encoding='UTF-8'?>
<sites>
  <site>
    <id>default</id>
    <url>https://mirrors.tuna.tsinghua.edu.cn/jenkins/updates/update-center.json</url>
  </site>
</sites>
EOF

# 设置github代理
echo "$(sed "/# GitHub520 Host Start/Q" /etc/hosts && curl https://raw.hellogithub.com/hosts)" > /etc/hosts

# 生成github ssh公私密钥
ssh-keygen -t ed25519 -f github_ed25519  -C "1827945911@qq.com"

# maven容器内部路径
/var/jenkins_home/tools/apache-maven-3.9.0

# jdk安装插件账户密码填写错误重新输入需要删除 hudson.tools.JDKInstaller.xml
rm -rf /var/jenkins_home/hudson.tools.JDKInstaller.xml

# 默认jdk11路径
whereis javawitch java
/opt/java/openjdk/bin/java

```
