# k8s

## Requirements

* java 21
* docker
* minikube (`brew install minikube`)
* kubectl (included in minikube)
* helm (`brew install helm`)

## Components

![components](_res/components.png)

## Deploy

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
minikube tunnel
```

### Check

```bash
open http://localhost/api/hello
```

### Dashboard

```bash
minikube dashboard
```
