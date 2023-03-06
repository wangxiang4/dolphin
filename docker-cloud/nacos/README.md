
```bash
#配置docker镜像代理
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": [
    "https://bd6wzfvy.mirror.aliyuncs.com",
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com"
  ]
}

EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```
