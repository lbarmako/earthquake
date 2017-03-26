EarthquakesApp
================================
An API allows mobile clients to read recent earthquake data. 
A secured API to create new earthquake data.

# Local setup

* Dev environment
* DNS
* Database

## Dev environment
If you have Java 8, Docker, Gradle installed and internet connectivity you should be able to run this.
Project is relatively IDE agnostic (but IntelliJ rocks).
 
Make sure you run `./gradlew` & make sure tests + quality checks pass prior to each commit.  If you try to push and 
someone else has committed during that time, make sure you pull and run your tests again successfully before to trying 
to push again.

## DNS
Run command `docker-machine ip` and obtain ip address. (eg. 127.0.0.1)
Use obtained ip to add a line in your  hosts file, e.g.
```
127.0.0.1 mysql
```

## Database
MySQL can be started from a docker container.  At the command line, `docker-compose up -d mysql` to start.

`docker-compose.yml` defines the container. 

Default database name is `earthquakes` with username and password set in yml.  

### Migrations
See the `data` subproject

# Run Tests
`./gradlew build` will run tests.
(Note. on Windows I had to run `./gradlew build --console plain` due to a bug in Gradle on Windows 10)
 
Integration tests start a DropwizardTestSupport object that starts a jetty container & connects to DB in test. 
Flyway migrations and rollbacks happen in there.

# Docker locally
## Dont stop containers after build
Add property `-P docker-compose.dontStopContainers` to keep your Mysql & SFTP docker containers open after a test run