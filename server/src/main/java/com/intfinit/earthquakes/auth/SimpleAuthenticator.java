package com.intfinit.earthquakes.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

import static com.intfinit.earthquakes.auth.Roles.ADMIN;
import static com.intfinit.earthquakes.auth.Roles.USER;

public class SimpleAuthenticator implements Authenticator<BasicCredentials, SimplePrincipal> {

    public static final String UPDATE_USER_PREFIX = "update_";

    @Override
    public Optional<SimplePrincipal> authenticate(BasicCredentials credentials)
            throws AuthenticationException {

        SimplePrincipal user = new  SimplePrincipal(credentials.getUsername());
        if (!credentials.getUsername().startsWith(UPDATE_USER_PREFIX)) {
            user.getRoles().add(USER);
        } else {
            user.getRoles().add(ADMIN);
        }

        return Optional.of(user);
    }
}
