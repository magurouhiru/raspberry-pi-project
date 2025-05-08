# Raspberry Pi Project
Raspberry Pi でいろいろやる用のプロジェクト  

## web
Raspberry Pi で動かすweb サーバーのためのプロジェクト  

### サーバー
axum で作成  
[詳細はこちら](./web/README.md)

### frontend
Angular で作成  
[詳細はこちら](./web/frontend/README.md)

# 環境設定とか
## Raspberry Pi の設定
### Raspberry Pi のOS 取得
下記から`Raspberry Pi Imager`をインストール
https://www.raspberrypi.com/software/

ユーザー名とパスワードを設定

### VNC 接続
Raspberry Pi Configuration からVNC を有効  
Microsoft Store からRealVNC Viewer をインストール  
IP アドレスを指定し、ユーザー名とパスワードから接続  

### ファイアウォール の設定
`sudo apt install ufw`でインストール  
`sudo ufw default deny`デフォルトで拒否
`sudo nano /etc/default/ufw`で`IPV6=no`に変更しIPV6を使用しない(なんか色々面倒らしい)  
`sudo ufw limit from 192.168.xx.0/24 to any port 5900`でローカル環境からのVNC接続のみ許可(xxは変えるかも)  
`sudo ufw limit from 192.168.xx.0/24 to any port 22`でローカル環境からのssh接続のみ許可  
`sudo ufw status`を実行して確認  
```
Status: active

To                         Action      From
--                         ------      ----
5900                       LIMIT       192.168.11.0/24           
22                         LIMIT       192.168.11.0/24     

```
`systemctl is-enabled ufw`を実行して自動起動の確認  
```
enabled
```
LIMIT はデフォルトで30秒に6回らしい。  
けどRealVNC Viewer だと1回ミスるとしばらくつながらない。(何回かトライしている？)  
