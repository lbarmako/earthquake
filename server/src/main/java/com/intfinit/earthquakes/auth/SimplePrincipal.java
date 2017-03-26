package com.intfinit.earthquakes.auth;

import com.google.common.collect.Lists;

import java.security.Principal;
import java.util.List;

public class SimplePrincipal implements Principal {

    private String username;

    private List<String> roles;

    public SimplePrincipal(String username) {
        this.username = username;
        this.roles = Lists.newArrayList();
    }

    @Override
    public String getName() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public boolean isUserInRole(String roleToCheck) {
        return roles.contains(roleToCheck);
    }
}