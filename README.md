# k8s

## Requirements

* docker
* minikube
* helm
* kubectl

## Deploy

### Set Public IP Address of Your Host

```bash
export PUBLIC_HOST=192.168.0.118
```

### Reset Minikube

```bash
minikube delete
minikube start --cpus=8 --memory=4G
minikube addons enable ingress
```

### Build Services

```bash
(
  cd _deploy
  eval $(minikube docker-env)
  ./build-images.sh
)
```

### Deploy Services

```bash
(
  cd _deploy
  ./deploy-services.sh
)
```

### Tunnel to Host

```bash
minikube tunnel --bind-address="$PUBLIC_HOST"
```
