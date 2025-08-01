コマンド
```
# k3s
# 起動・停止
sudo systemctl start k3s
sudo systemctl stop k3s

# 状態取得・削除
kubectl get all
kubectl delete all --all
kubectl logs pod/
kubectl logs --previous pod/
kubectl exec -it pod/pi-web-api-server-XXXXXXX -- tail -n 100 /opt/api-server/logs/application.log
kubectl describe pod/

# secret設定
kubectl create secret generic db-secrets --from-literal=db-password='super-secret-password'

# 色々追加
helm dependency update ./helm/pi-web/
helm upgrade --install pi-web ./helm/pi-web -n default
#helm upgrade --install frontend ./helm/pi-web_frontend/ --namespace default
#helm upgrade --install api-server ./helm/pi-web_api-server/ --namespace default

# image取得・削除
sudo k3s ctr images ls
sudo k3s ctr images pull ghcr.io/magurouhiru/raspberry-pi-project/pi-web_api-server:0.0.0-wip
sudo k3s ctr images rm ghcr.io/magurouhiru/raspberry-pi-project/pi-web_api-server:0.0.0-wip
sudo crictl images
sudo crictl rmi ghcr.io/magurouhiru/raspberry-pi-project/pi-web_frontend:0.0.0-wip
sudo crictl rmi ghcr.io/magurouhiru/raspberry-pi-project/pi-web_api-server:0.0.0-wip

# nerdctl
sudo systemctl start containerd
sudo systemctl stop containerd
sudo nerdctl run --rm -it -p 30080:9000 ghcr.io/magurouhiru/raspberry-pi-project/pi-web_api-server:0.0.0-wip

git restore .
```
