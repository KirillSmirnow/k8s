#!/usr/bin/env bash

default_namespace="$1"
if [[ "$default_namespace" = "" ]]; then
  echo "Usage: ./switch-default-namespace.sh default_namespace"
  exit 1
fi

echo "Switching default namespace to $default_namespace"

all_namespaces=$(helm list -A | cut -f2 | sort | uniq)

for file in services/*; do
  service=$(basename "${file%.*}")
  echo "Service $service"
  release="$service-default"

  # Disable default ingresses in all namespaces
  for namespace in $all_namespaces; do
    helm uninstall "$release" --namespace="$namespace"
  done

  # Enable default ingress in default_namespace
  helm install "$release" ./helm-chart-default-ingress --namespace="$default_namespace" --set="service=$service"
done
