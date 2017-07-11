# Connect4 Coding Test

## Background
This project is built with Scala on the backend and simple jQuery, JS, and CSS on the front end.  Back end code was built with TDD.  The deployment artifact is an executable JAR file containing all of the dependencies and static content, in keeping with 12-factor design.

## Getting Started

Developed and tested with the following software:

- a bash-like shell (macOS Sierra 10.12.3)
- cloud foundry command line interface installed

```sh
$ brew tap cloudfoundry/tap
$ brew install cf-cli
$ cf -v
cf version 6.23.0+c7866be18-2016-12-22
```

**To Run Locally (Web)**
`./sbt ~jetty:start`

**To Test**
`./sbt test`

**To Package for Deployment**
`./sbt clean assembly`

**To Push to CloudFoundry**
`cf push`

## Libraries
- Scala 2.11.x
- jQuery
- specs2
- logback
- Jetty 9
