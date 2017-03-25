package com.intfinit.earthquakes.dao;


import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;

import java.util.List;
import java.util.Optional;

public interface EarthquakeRecordDao extends GenericDao<EarthquakeRecord, Long> {

    void save(EarthquakeRecord earthquakeRecord);

    Optional<EarthquakeRecord> findByNaturalId(EarthquakeRecord earthquakeRecord);

    List<EarthquakeRecord> getEarthquakeRecords(Double minMagnitude, Integer limit);

}

