package com.intfinit.earthquakes.auth;

import io.dropwizard.auth.basic.BasicCredentials;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.intfinit.earthquakes.auth.Roles.ADMIN;
import static com.intfinit.earthquakes.auth.Roles.USER;
import static com.intfinit.earthquakes.auth.SimpleAuthenticator.UPDATE_USER_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;


public class SimpleAuthenticatorTest {

    private static final String GENERAL_USERNAME = "any user";
    private static final String UPDATE_USERNAME = UPDATE_USER_PREFIX + "user";
    private SimpleAuthenticator simpleAuthenticator;

    private BasicCredentials basicCredentials;

    @Before
    public void setUp() throws Exception {
        simpleAuthenticator = new SimpleAuthenticator();
    }

    @Test
    public void authenticateReturnsGeneralUser() throws Exception {
        basicCredentials = new BasicCredentials(GENERAL_USERNAME, "");
        Optional<SimplePrincipal> simplePrincipalOptional = simpleAuthenticator.authenticate(basicCredentials);
        assertThat(simplePrincipalOptional).isPresent();
        SimplePrincipal simplePrincipal = simplePrincipalOptional.orElseThrow(IllegalStateException::new);
        assertThat(simplePrincipal.getRoles()).hasSize(1).first().isEqualTo(USER);
    }

    @Test
    public void authenticateReturnsAdminUser() throws Exception {
        basicCredentials = new BasicCredentials(UPDATE_USERNAME, "");
        Optional<SimplePrincipal> simplePrincipalOptional = simpleAuthenticator.authenticate(basicCredentials);
        assertThat(simplePrincipalOptional).isPresent();
        SimplePrincipal simplePrincipal = simplePrincipalOptional.orElseThrow(IllegalStateException::new);
        assertThat(simplePrincipal.getRoles()).hasSize(1).first().isEqualTo(ADMIN);
    }

}