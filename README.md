# Connect4 Coding Test

## Background
This project is built with Scala on the backend and simple jQuery, JS, and CSS on the front end.  Back end code was built with TDD.  The deployment artifact is an executable JAR file containing all of the dependencies and static content, in keeping with 12-factor design.  The game supports multiple players and does not require a database because it is stateless -- all state is maintained in the client's DOM and state transitions occur via the API.  The computer player by default is set to an advanced computer player that will try to win or block the player from winning.  The intelligence is adjustable, but currently looks ahead 4 moves.

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
- Scala 2.11.x - host language
- jQuery - front end AJAX and DOM manipulation
- specs2 - testing framework
- logback - logging backend
- Jetty 9 - embedded web server
- Scalatra - HTTP framework
- Jackson/json4s - JSON marshalling
- sbt-assembly - packaging of code and static content in executable JAR file
- scalatra-sbt - integration of Scalatra with the build tool
- sbt 0.13.3 - build tool
- Oracle JDK 8 - vm, compiler, platform 
