package com.intfinit.earthquakes.modules;

import com.google.inject.AbstractModule;
import com.intfinit.earthquakes.resources.EarthquakeResource;

import static com.google.inject.Scopes.SINGLETON;

public class JerseyResourceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EarthquakeResource.class).in(SINGLETON);
    }
}
