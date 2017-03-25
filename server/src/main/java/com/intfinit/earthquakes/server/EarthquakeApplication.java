package com.intfinit.earthquakes.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.intfinit.commons.dropwizard.logging.filter.alt.DynamicBodyLoggingFilter;
import com.intfinit.earthquakes.config.EarthquakeApplicationConfiguration;
import com.intfinit.earthquakes.dao.DaoModule;
import com.intfinit.earthquakes.resources.EarthquakeResource;
import com.intfinit.earthquakes.server.health.MysqlJpaHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static com.google.inject.Stage.PRODUCTION;
import static org.glassfish.jersey.server.ServerProperties.BV_SEND_ERROR_IN_RESPONSE;

public class EarthquakeApplication extends Application<EarthquakeApplicationConfiguration> {

    private static final Logger LOG = LoggerFactory.getLogger(EarthquakeApplication.class);
    private static final String BODY_LOGGER_NAME = "EarthquakeApplicationBody";

    private Injector injector;

    public static void main(String[] args) throws Exception {
        new EarthquakeApplication().run(args);
    }

    private static DynamicBodyLoggingFilter createBodyLoggingFilter(Logger logger) {
        //enable json request/response logging
        return new DynamicBodyLoggingFilter(logger, 16 * 1024, null, () -> true);
    }

    private static String getVersion() {
        return EarthquakeApplication.class.getPackage().getImplementationVersion();
    }

    @Override
    public void initialize(Bootstrap<EarthquakeApplicationConfiguration> bootstrap) {
        enableEnvironmentVariableOverride(bootstrap);
    }

    public Injector getInjector() {
        return injector;
    }

    @Override
    public void run(EarthquakeApplicationConfiguration config, Environment env) {
        LOG.info("{}Version={}", getClass().getSimpleName(), getVersion());

        injector = setupGuice(config);

        configureObjectMapper(env);
        configureJersey(env, injector);
        setupHealthChecks(env, injector);
    }

    private Injector setupGuice(EarthquakeApplicationConfiguration config) {

        Injector persistenceRootInjector = Guice.createInjector(PRODUCTION, config.getJpaModule());
        PersistService persistService = persistenceRootInjector.getInstance(PersistService.class);
        persistService.start();

        return injector = persistenceRootInjector.createChildInjector(new DaoModule(), new JerseyResourcesModule());
    }

    private void enableEnvironmentVariableOverride(Bootstrap<EarthquakeApplicationConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false, false)
                )
        );
    }

    private void configureObjectMapper(Environment env) {
        env.getObjectMapper().enable(INDENT_OUTPUT).disable(WRITE_DATES_AS_TIMESTAMPS); // ISO-8601 Date formats
    }

    private void setupHealthChecks(Environment env, Injector injector) {
        env.healthChecks().register("mysql", injector.getInstance(MysqlJpaHealthCheck.class));
    }

    @SuppressWarnings("unused")
    private void configureJersey(Environment environment, Injector injector) {
        JerseyEnvironment jersey = environment.jersey();
        jersey.register(createBodyLoggingFilter(LoggerFactory.getLogger(BODY_LOGGER_NAME)));

        jersey.register(injector.getInstance(EarthquakeResource.class));

        //enable returning validation errors
        jersey.enable(BV_SEND_ERROR_IN_RESPONSE);
    }


}
