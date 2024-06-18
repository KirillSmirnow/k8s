# k8s

## Requirements

* java 21
* docker
* minikube (`brew install minikube`)
* kubectl (included in minikube)
* istioctl (`brew install istioctl`)
* helm (`brew install helm`)

## Components

![components](_res/components.png)

## Deploy

### Reset Minikube

```bash
minikube delete
minikube start --cpus=8 --memory=8G
minikube addons enable metrics-server
istioctl install -y
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

### Configure Ingress

```bash
(
  cd ansible
  ansible-playbook playbook.yml -e "active_color=blue"
)
```

### Tunnel to Host

```bash
minikube tunnel
```

### Check

```bash
open http://localhost/api/main
```

### Dashboard

```bash
minikube dashboard
```
