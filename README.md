# k8s

## Requirements

* java
* docker
* minikube
* helm
* kubectl

## Components

![components](_res/components.png)

## Deploy

### Set Public IP Address of Your Host

```bash
export PUBLIC_HOST=192.168.0.118
```

### Reset Minikube

```bash
minikube delete
minikube start --cpus=8 --memory=8G
minikube addons enable metrics-server
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
minikube tunnel --bind-address="0.0.0.0"
```

### Check

```bash
open http://localhost/api/hello
```

### Dashboard

```bash
minikube dashboard
```
