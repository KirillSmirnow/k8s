#!/usr/bin/env bash

deploy() {
  service="$1"
  namespace="$2"

  kubectl create namespace "$namespace"
  kubectl label namespace "$namespace" istio-injection=enabled
  echo "Deploying $service.$namespace"

  if helm status "$service" --namespace="$namespace"; then
    cmd="upgrade"
  else
    cmd="install"
  fi
  helm "$cmd" "$service" ./helm-chart -f "services/$service.yml" --namespace="$namespace"
}

deploy "main" "green"
deploy "main" "blue"
deploy "hello" "green"
deploy "hello" "blue"
deploy "time" "green"
deploy "time" "blue"

kubectl apply -f istio-external-gateway.yml
kubectl apply -f istio-internal-gateway.yml
