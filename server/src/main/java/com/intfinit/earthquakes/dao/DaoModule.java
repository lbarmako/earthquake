package com.intfinit.earthquakes.dao;

import com.google.inject.AbstractModule;
import com.intfinit.earthquakes.services.EarthquakeRecordService;
import com.intfinit.earthquakes.transformers.EarthquakeRecordListToResponseTransformer;
import com.intfinit.earthquakes.transformers.EarthquakeRecordModelToRecordTransformer;
import com.intfinit.earthquakes.transformers.EarthquakeRecordToModelTransformer;

import static com.google.inject.Scopes.SINGLETON;

public class DaoModule extends AbstractModule {

    @Override
    protected void configure() {
        bindDaos();
        bindTransformers();
        bindServices();
    }

    private void bindTransformers() {
        bind(EarthquakeRecordToModelTransformer.class);
        bind(EarthquakeRecordListToResponseTransformer.class);
        bind(EarthquakeRecordModelToRecordTransformer.class);
    }

    private void bindDaos() {
        bind(EarthquakeRecordDao.class).to(EarthquakeRecordDaoImpl.class);
    }

    private void bindServices() {
        bind(EarthquakeRecordService.class).in(SINGLETON);
    }

}
