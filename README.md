# Raspberry Pi Project
Raspberry Pi でいろいろやる用のプロジェクト  

## web
Raspberry Pi で動かすweb サーバーのためのプロジェクト  

### server
axum で作成  
[詳細はこちら](./web/backend/README.md)

### frontend
Angular で作成  
[詳細はこちら](./web/frontend/README.md)

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
