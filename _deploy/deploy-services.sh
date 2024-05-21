#!/usr/bin/env bash

for file in services/*; do
  service=$(basename "${file%.*}")
  echo "Service $service"
  if helm status "$service"; then
    cmd="upgrade"
  else
    cmd="install"
  fi
  helm "$cmd" "$service" ./helm-chart -f "$file"
done

kubectl apply -f ingress.yml
