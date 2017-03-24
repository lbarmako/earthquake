package com.intfinit.earthquakes.dao;


import com.google.inject.persist.Transactional;
import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;

import java.util.Optional;

public interface EarthquakeRecordDao extends GenericDao<EarthquakeRecord, Long> {

    void save(EarthquakeRecord earthquakeRecord);

    Optional<EarthquakeRecord> findByNaturalId(EarthquakeRecord earthquakeRecord);
}
