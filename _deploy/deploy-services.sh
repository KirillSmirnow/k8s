#!/usr/bin/env bash

namespace="$1"
if [[ "$namespace" = "" ]]; then namespace="green"; fi
kubectl create namespace "$namespace"
kubectl label namespace "$namespace" istio-injection=enabled
echo "Namespace=$namespace"

for file in services/*; do
  service=$(basename "${file%.*}")
  echo "Service $service"
  if helm status "$service" --namespace="$namespace"; then
    cmd="upgrade"
  else
    cmd="install"
  fi
  helm "$cmd" "$service" ./helm-chart -f "$file" --namespace="$namespace"
done

kubectl apply -f istio-external-gateway.yml
