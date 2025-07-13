コマンド
```
# k3s
sudo systemctl start k3s
sudo systemctl stop k3s

kubectl get all
kubectl delete all --all

helm upgrade --install frontend ./helm/pi-web_frontend/ --namespace default
helm upgrade --install api-server ./helm/pi-web_api-server/ --namespace default

sudo crictl rmi ghcr.io/magurouhiru/raspberry-pi-project/pi-web_api-server:0.0.0-wip
sudo crictl rmi ghcr.io/magurouhiru/raspberry-pi-project/pi-web_frontend:0.0.0-wip

# nerdctl
sudo systemctl start containerd
sudo systemctl stop containerd
sudo nerdctl run --rm -it -p 30080:9000 ghcr.io/magurouhiru/raspberry-pi-project/pi-web_api-server:0.0.0-wip
```
