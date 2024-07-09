_targets:
  @just --list --unsorted --list-heading $'Available targets:\n' --list-prefix "  "

# [tests] run tests for the corresponding directory
check project="":
    #!/usr/bin/env sh
    if [ -z `echo {{project}}` ]; then
      ./gradlew check
    else
      ./gradlew :{{project}}:check
    fi

# [tests] approve `*.actual` files in the `project` directory; skip parameter to do it for the entire repo
approve:
    ./gradlew approve

# [gradle] refresh dependency versions
versions:
    ./gradlew refreshVersions
