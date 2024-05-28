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


check-all:
    #!/usr/bin/env python3
    import re, subprocess

    # Open and read the file
    with open("settings.gradle.kts", 'r') as file:
        file_content = file.read()

    # Use regular expression to find the pattern
    matches = re.findall(r'include\(([^)]+)\)', file_content)

    # Initialize an empty list to hold the names
    names = []

    # Process each match
    for match in matches:
        # Split the match by comma and strip spaces and quotes
        extracted_names = [name.strip().strip('"') for name in match.split(',')]
        names.extend(extracted_names)

    for name in names:
            # Construct the command
            command = f"./gradlew :{name}:check"

            # Print the command to be executed (optional)
            print(f"Executing: {command}")

            # Execute the command
            try:
                result = subprocess.run(command, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
                # Print the output of the command
                print(result.stdout.decode())
            except subprocess.CalledProcessError as e:
                # If the command failed, print the error
                print(f"Error executing {command}: {e.stderr.decode()}")

