# monorepo-poc

## Technology choice

### Kotlin

### [Http4k](https://www.http4k.org/)

One of the main
server [Kotlin side frameworks](https://kotlinlang.org/docs/server-overview.html#frameworks-for-server-side-development-with-kotlin)
All the knowledge needed for this exercise can be found in
this [simple tutorial](https://www.youtube.com/watch?v=FVvn-aFO--Q&ab_channel=DmitryKandalov)

### Gradle

Gradle Build Tool is a fast, dependable, and adaptable open-source build automation tool with an elegant and extensible
declarative build language.

#### How to upgrade the version

1. set `gradleVersion` to the target [release version](https://gradle.org/releases/)
2. set `distributionSha256Sum` to the `Complete (-all) ZIP` [checksum](https://gradle.org/release-checksums/) of the
   corresponding version
3. Run `./gradlew wrapper`, test that everything is fine with `just check` and commit all changes

## Configuration

### Python

1. Install Python. For example [pyenv](https://github.com/pyenv/pyenv):
1. Install `pyenv`
2. Go to the base folder
3. Run: `pyenv install` (this will install the version configured in `.python-version` file)
2. Create `venv` folder manually by running `python3 -m venv venv`
3. Configure IDE. For example with IntelliJ:
1. Go to `File -> Project Structure`. Under `SDKs` add `Python SDK` and choose the executable of python
   that we just created (`venv/bin/python3`)
2. To get the interpreter properly configured, you may need
   to [add the facet manually](https://www.jetbrains.com/help/idea/adding-support-for-frameworks-and-technologies.html#manually-add-facet-to-module)

## Ideas

In progress:

- create tests and apps that spin up a db

Gradle:

- Configure version manager for buildSrc
- Test plugin https://plugins.gradle.org/plugin/com.autonomousapps.dependency-analysis
- separated Gradle tasks to run unit and integration tests

Others:

- generate openapi metadata
- create a docker image with all projects together
- create an autopipeline
- trigger some pipelines per push
- create tests using proxying
- test different docker images
- create a live documentation
