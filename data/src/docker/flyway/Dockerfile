#FROM hiogawa/flyway
FROM java:8
MAINTAINER Intfinit

ENV FLYWAY_VERSION 4.0.3
RUN wget https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/$FLYWAY_VERSION/flyway-commandline-$FLYWAY_VERSION.tar.gz
RUN tar -xzf flyway-commandline-$FLYWAY_VERSION.tar.gz
RUN mv /flyway-$FLYWAY_VERSION /flyway

ENV PATH /flyway:$PATH
WORKDIR /flyway

ENV DOCKERIZE_VERSION v0.2.0
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

COPY entrypoint.sh /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
CMD ["migrate"]