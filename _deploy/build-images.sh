#!/usr/bin/env bash

IMAGE_PREFIX="k8s/"

dockerfile="$PWD/image/Dockerfile"

for dir in ../*; do
  if [[ -f "$dir/gradlew" ]]; then
    service=$(basename "$dir")
    echo "Service $service"
    (
      cd "$dir" || exit
      ./gradlew clean build
      rm build/libs/*plain.jar
      mv build/libs/*.jar .
      docker build -f "$dockerfile" -t "$IMAGE_PREFIX$service" .
      rm ./*.jar
    )
  fi
done
