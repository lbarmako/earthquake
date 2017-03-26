package com.intfinit.earthquakes.auth;

import io.dropwizard.auth.Authorizer;

public class SimpleAuthorizer implements Authorizer<SimplePrincipal> {

    @Override
    public boolean authorize(SimplePrincipal principal, String role) {

        return principal.getRoles().contains(role);
    }
}