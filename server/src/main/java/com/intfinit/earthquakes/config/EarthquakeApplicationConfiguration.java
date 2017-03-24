package com.intfinit.earthquakes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.google.inject.persist.jpa.JpaPersistModule;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

public class EarthquakeApplicationConfiguration extends Configuration {

    public static final String JDBC_URL = "javax.persistence.jdbc.url";
    public static final String JDBC_USER = "javax.persistence.jdbc.user";
    public static final String JDBC_PASSWORD = "javax.persistence.jdbc.password";
    public static final String JDBC_DRIVER = "javax.persistence.jdbc.driver";

    private static final Logger LOG = LoggerFactory.getLogger(EarthquakeApplicationConfiguration.class);

    @JsonProperty("database")
    @Valid
    public DataSourceFactory dataSourceFactory;

    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    public JpaPersistModule getJpaModule() {
        ImmutableMap<String, String> properties = ImmutableMap.of(
                JDBC_DRIVER, dataSourceFactory.getDriverClass(),
                JDBC_URL, dataSourceFactory.getUrl(),
                JDBC_USER, dataSourceFactory.getUser(),
                JDBC_PASSWORD, dataSourceFactory.getPassword()
        );
        LOG.debug(properties.toString());
        return new JpaPersistModule("DefaultUnit").properties(properties);
    }
}
