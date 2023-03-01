FROM jenkins/jenkins:2.375.3-lts-jdk11

USER root

# 将工具加入容器
COPY "./tools/apache-maven-3.9.0" /var/jenkins_home/tools/apache-maven-3.9.0/

# 设置权限
RUN chmod -R 777 /var/jenkins_home/tools

