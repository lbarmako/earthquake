package com.intfinit.earthquakes;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.intfinit.earthquakes.config.EarthquakeApplicationConfiguration;
import com.intfinit.earthquakes.server.EarthquakeApplication;
import com.intfinit.earthquakes.util.DatabaseHelper;
import com.intfinit.earthquakes.util.ProjectRoot;
import io.dropwizard.testing.DropwizardTestSupport;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import java.util.HashSet;
import java.util.Set;

import static io.dropwizard.testing.ConfigOverride.config;

public class ApplicationRule implements TestRule {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationRule.class);

    private static final DropwizardTestSupport<EarthquakeApplicationConfiguration> SUPPORT =
            new DropwizardTestSupport<>(
                    EarthquakeApplication.class,
                    configPath(),
                    config("server.applicationConnectors[0].port", "0"),
                    config("server.adminConnectors[0].port", "0"),
                    config("logging.appenders[1].threshold", "ALL")
            );

    public static String configPath() {
        return ProjectRoot.projectRootRelative("server/src/main/resources/earthquakes.yml").toString();
    }

    private static Set<Class> executed = new HashSet<>();

    private final Reload reload;
    private Client client;
    private String baseUrl;
    private EarthquakeApplicationConfiguration configuration;
    private DatabaseHelper dbHelper;

    public ApplicationRule(Reload reload) {
        this.reload = reload;
        this.dbHelper = DatabaseHelper.getHelper();
    }

    public ApplicationRule() {
        this(Reload.NEVER);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                handleReload(description.getTestClass());
                base.evaluate();
            }
        };
    }

    private void handleReload(Class testClass) {
        if (reload == Reload.PER_TEST) {
            reloadDBFixtures();
        } else if (reload == Reload.PER_TEST_CLASS && !executed.contains(testClass)) {
            executed.add(testClass);
            reloadDBFixtures();
        }
    }

    public void reloadDBFixtures() {
        LOG.info("Reloading DB fixtures");
        dbHelper.loadFixtures();
    }

    protected void before() throws Throwable {
        dbHelper.initialise();
        SUPPORT.before();

        JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
        jsonProvider.setMapper(SUPPORT.getObjectMapper());
        client = JerseyClientBuilder.newBuilder().register(jsonProvider).build();

        baseUrl = "http://localhost:" + SUPPORT.getPort(0);
        configuration = SUPPORT.getConfiguration();
    }

    public WebTarget target(String url) {
        return client.target(baseUrl + url);
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getAppPort() {
        return SUPPORT.getLocalPort();
    }

    public int getAdminPort() {
        return SUPPORT.getAdminPort();
    }

    public enum Reload {
        NEVER,
        PER_TEST,
        PER_TEST_CLASS
    }

    public EarthquakeApplication getApplication() {
        return SUPPORT.getApplication();
    }

    public EarthquakeApplicationConfiguration getConfiguration() {
        return configuration;
    }

    public Provider<EntityManager> getEntityManagerProvider() {
        return getApplication().getInjector().getProvider(EntityManager.class);
    }

    public Query createteNativeQuery(String sqlQuery) {
        return getEntityManagerProvider().get().createNativeQuery(sqlQuery);
    }
}
