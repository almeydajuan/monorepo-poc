# [tests] run tests for the corresponding directory
check project="":
    #!/usr/bin/env sh
    ./gradlew :{{project}}:check

# [tests] approve `*.actual` files in the `project` directory; skip parameter to do it for the entire repo
approve project="":
  #!/usr/bin/env sh
  export FROM=actual
  export TO=approved
  find "./{{project}}" -name "*.$FROM" -exec bash -c 'mv -v "$1" "${1%.$FROM}".$TO' - '{}' \;
