#!/usr/bin/env bash

for file in services/*; do
  service=$(basename "${file%.*}")
  echo "Service $service"
  if helm status "$service"; then
    cmd="upgrade"
  else
    cmd="install"
  fi
  helm "$cmd" "$service" ./helm-chart -f "$file" --set "publicHost=$PUBLIC_HOST"
done

kubectl apply -f ingress.yml
