#!/bin/bash
dockerize -wait tcp://mysql:3306 -timeout 30s
exec flyway -url=$FLYWAY_URL -user=$FLYWAY_USER -password=$FLYWAY_PASSWORD -locations=filesystem:/flyway/sql,filesystem:/flyway/callbacks $@