package com.intfinit.earthquakes.dao;

import com.google.inject.AbstractModule;

public class DaoModule extends AbstractModule {

    @Override
    protected void configure() {
        bindDaos();
        bindServices();
    }

    private void bindDaos() {
        bind(EarthquakeRecordDao.class).to(EarthquakeRecordDaoImpl.class);
    }

    private void bindServices() {
//        bind(JobService.class).in(Singleton.class);
    }

}
