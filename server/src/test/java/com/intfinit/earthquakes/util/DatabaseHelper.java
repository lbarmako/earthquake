package com.intfinit.earthquakes.util;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Files;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.db.DataSourceFactory;
import org.flywaydb.core.Flyway;
import org.jooq.lambda.Unchecked;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.intfinit.earthquakes.util.ProjectRoot.projectRoot;

public class DatabaseHelper {

    private static final String PATH_TO_DROPWIZARD_CONFIG = "server/src/main/resources/earthquakes.yml";

    private static DatabaseHelper helper = new DatabaseHelper(projectRoot());

    public static DatabaseHelper getHelper() {
        return helper;
    }

    private JsonNode configTree;
    private ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    private Flyway flyway;
    private boolean initialised;
    private File projectRoot;
    private DataSourceFactory database;
    private DataSource dataSource;

    public DatabaseHelper(File projectRoot) {
        setDatabase(getConfiguration(projectRoot, "database", DataSourceFactory.class));
        flyway = createFlyway();
        this.projectRoot = projectRoot;
    }

    private void setDatabase(DataSourceFactory database) {
        this.database = database;
        dataSource = database.build(new MetricRegistry(), "database");
    }

    private Flyway createFlyway() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations(pathToDataMigrations());
        return flyway;
    }

    private static String pathToDataMigrations() {
        return Unchecked.supplier(() -> "filesystem:" + ProjectRoot.projectRootRelative("data/src/main/resources/db/migration").getCanonicalPath()).get();
    }

    private <T> T getConfiguration(File projectRoot, String configPath, Class<T> clazz) {
        return Unchecked.supplier(() -> mapper.treeToValue(getConfigurationTree(projectRoot).path(configPath), clazz)).get();
    }

    private JsonNode getConfigurationTree(File projectRoot) throws IOException {
        if (configTree == null) {
            File file = new File(projectRoot.getCanonicalPath(), PATH_TO_DROPWIZARD_CONFIG);
            String config = new EnvironmentVariableSubstitutor(false, false).replace(Files.toString(file, Charsets.UTF_8));
            configTree = mapper.readTree(config);
        }
        return configTree;
    }

    public void initialise() {
        if (initialised) {
            return;
        }
        initialised = true;

        flyway.setBaselineOnMigrate(true);
        flyway.clean();
        flyway.migrate();

        loadFixtures();
    }

    public void loadFixtures() {
        loadFixtures(new File(projectRoot, "server/src/test/resources/db/fixture/integration-test.sql"));
    }

    public void loadFixtures(String file) {
        loadFixtures(new File(projectRoot, file));
    }

    private void loadFixtures(File file) {
        Unchecked.runnable(() -> {
            try (Connection connection = getConnection()) {
                String script = Files.toString(file, Charsets.UTF_8);

                for (String sql : Splitter.on(";").trimResults().omitEmptyStrings().split(script)) {
                    try (Statement statement = connection.createStatement()) {
                        statement.executeUpdate(sql);
                    }
                }
            }
        }).run();
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
