#!/usr/bin/env bash

test_paths=(
  "/api/main"
  "/api/main/"
  "/api/main/apple/orange"
  "/api/main-blue"
  "/api/main-blue/"
  "/api/main-blue/apple/orange"
)

expected_rewritten_paths=(
  "/"
  "/"
  "/apple/orange"
  "/"
  "/"
  "/apple/orange"
)

(
  cd ansible || exit
  ansible-playbook playbook.yml -e "color=blue"
)

for test_path_index in "${!test_paths[@]}"; do
  test_path="${test_paths[test_path_index]}"
  expected_rewritten_path="${expected_rewritten_paths[test_path_index]}"

  response=$(curl "localhost$test_path" -v 2>&1)
  status=$(echo "$response" | grep "< HTTP" | grep -o "[0-9]\{3\}")
  actual_rewritten_path=$(echo "$response" | grep -o "path[^,]\+" | head -n1 | cut -d":" -f2 | grep -o "[^\"]\+")

  if [[ "$status" = "200" && "$actual_rewritten_path" = "$expected_rewritten_path" ]]; then
    test_result=" OK "
  else
    test_result="FAIL"
  fi

  echo "$test_result | $status $test_path -> $actual_rewritten_path"
done
