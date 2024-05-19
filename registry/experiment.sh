#!/usr/bin/env bash

victim=$(kubectl get pods -o name | grep hello | head -n1 | cut -d"/" -f2)
echo "Victim: $victim"

kubectl delete pod "$victim" &

while true; do
  http=$(curl -i "localhost/api/hello" 2>/dev/null | grep "HTTP/1.1" | tr -d "\r")

  up=$(curl "localhost/registry1/eureka/apps" 2>/dev/null | grep "$victim" -A4 | grep -c UP)
  down=$(curl "localhost/registry1/eureka/apps" 2>/dev/null | grep "$victim" -A4 | grep -c DOWN)

  echo "$(date) | $http | up=$up | down=$down"

  sleep 0.5
done
