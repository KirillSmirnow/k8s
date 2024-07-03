#!/usr/bin/env bash

URL="http://external.k8s"

test_paths=(
  "/main/"
  "/main/apple/orange"
  "/api/main/"
  "/api/main/apple/orange"
  "/api/v1/main/"
  "/api/v1/main/apple/orange"
)

expected_rewritten_paths=(
  "/"
  "/apple/orange"
  "/"
  "/apple/orange"
  "/"
  "/apple/orange"
)

for test_path_index in "${!test_paths[@]}"; do
  test_path="${test_paths[test_path_index]}"
  expected_rewritten_path="${expected_rewritten_paths[test_path_index]}"

  response=$(curl "$URL$test_path" -v 2>&1)
  status=$(echo "$response" | grep "< HTTP" | grep -o "[0-9]\{3\}")
  actual_rewritten_path=$(echo "$response" | grep -o "path[^,]\+" | head -n1 | cut -d":" -f2 | grep -o "[^\"]\+")

  if [[ "$status" = "200" && "$actual_rewritten_path" = "$expected_rewritten_path" ]]; then
    test_result=" OK "
  else
    test_result="FAIL"
  fi

  echo "$test_result | $status $test_path -> $actual_rewritten_path"
done
