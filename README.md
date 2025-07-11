# Raspberry Pi Project
Raspberry Pi でいろいろやる用のプロジェクト  

## pi-web
Raspberry Pi で動かすweb サーバーのためのプロジェクト  

### api-server
axum で作成  
[詳細はこちら](./pi-web/api-server/README.md)

### frontend
Angular で作成  
[詳細はこちら](./pi-web/frontend/README.md)

# 環境設定とか
## Raspberry Pi の設定
### Raspberry Pi のOS 取得
下記から`Raspberry Pi Imager`をインストール
https://www.raspberrypi.com/software/

ユーザー名とパスワードとsshを設定

### ファイアウォール の設定
`sudo apt install ufw`でインストール  
`sudo ufw default deny`デフォルトで拒否  
`sudo nano /etc/default/ufw`で`IPV6=no`に変更しIPV6を使用しない(なんか色々面倒らしい)  
`sudo ufw limit from 192.168.xx.0/24 to any port 22`でローカル環境からのssh接続のみ許可  
`sudo ufw status`を実行して確認  
```
Status: active

To                         Action      From
--                         ------      ----
22                         LIMIT       192.168.11.0/24

```
`systemctl is-enabled ufw`を実行して自動起動の確認  
```
enabled
```
LIMIT はデフォルトで30秒に6回らしい。  

### k3s
スワップ有効化  
`sudo dphys-swapfile swapoff`  
`sudo nano /etc/dphys-swapfile`  
CONF_SWAPSIZE=2048  
に変更して保存。  
`sudo dphys-swapfile setup`  
`sudo dphys-swapfile swapon`  

カーネルパラメータ追加  
`sudo nano /boot/firmware/cmdline.txt`  
cgroup_enable=cpuset cgroup_enable=memory cgroup_memory=1  
を後ろに追加(1行にまとめる)    
例：  
console=serial0,115200 console=tty1 root=PARTUUID=xxxxxx-02 rootfstype=ext4 elevator=deadline fsck.repair=yes rootwait  
→console=serial0,115200 console=tty1 root=PARTUUID=xxxxxx-02 rootfstype=ext4 elevator=deadline fsck.repair=yes rootwait cgroup_enable=cpuset cgroup_enable=memory cgroup_memory=1  
`sudo reboot`  
`ls /sys/fs/cgroup`でmemoryディレクトリがあればOKらしい

インストール  
`curl -sfL https://get.k3s.io | INSTALL_K3S_EXEC="--disable traefik --disable servicelb --disable metrics-server" sh -
`デフォルトだと（ラズパイ3bでは）とても重いので重要  
`journalctl -fu k3s.service`リアルタイムでログ出力  
`sudo kubectl get nodes`↓のようになればOK
```
$ sudo kubectl get nodes
NAME      STATUS   ROLES                  AGE   VERSION
electpi   Ready    control-plane,master   18h   v1.32.6+k3s1
```

sudoなしで使えるようにする(k9sでも必要)  
`mkdir -p ~/.kube`  
`sudo cp /etc/rancher/k3s/k3s.yaml ~/.kube/config`  
`sudo chown $(id -u):$(id -g) ~/.kube/config`  
`echo 'export KUBECONFIG=$HOME/.kube/config' >> ~/.bashrc`  

**k3s起動中はネットワーク帯域？ディスクIO？を圧迫するため重くなる**  
**ssh接続もきついので`sudo systemctl stop k3s`で切る方がいい**  

Helmインストール  
`curl -fsSL https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
`  
`helm version`確認  

k9sインストール  
`wget https://github.com/derailed/k9s/releases/download/v0.50.7/k9s_Linux_arm64.tar.gz -O k9s.tar.gz`最新のバージョンがあればそれに  
`tar -xzf k9s.tar.gz`  
`sudo mv k9s /usr/local/bin/`  
`k9s version`確認  
