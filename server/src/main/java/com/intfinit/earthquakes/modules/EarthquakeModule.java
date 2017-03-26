package com.intfinit.earthquakes.modules;

import com.google.inject.AbstractModule;
import com.intfinit.earthquakes.dao.EarthquakeRecordDao;
import com.intfinit.earthquakes.dao.EarthquakeRecordDaoImpl;
import com.intfinit.earthquakes.services.EarthquakeRecordService;
import com.intfinit.earthquakes.transformers.EarthquakeRecordListToResponseTransformer;
import com.intfinit.earthquakes.transformers.EarthquakeRecordModelToRecordTransformer;
import com.intfinit.earthquakes.transformers.EarthquakeRecordToModelTransformer;

import static com.google.inject.Scopes.SINGLETON;

public class EarthquakeModule extends AbstractModule {

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
