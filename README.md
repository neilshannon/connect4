# Connect4 Coding Test

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