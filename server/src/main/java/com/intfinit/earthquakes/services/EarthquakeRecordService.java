package com.intfinit.earthquakes.services;

import com.intfinit.earthquakes.dao.EarthquakeRecordDao;
import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import com.intfinit.earthquakes.model.EarthquakeRecordResponse;
import com.intfinit.earthquakes.transformers.EarthquakeRecordListToResponseTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;


public class EarthquakeRecordService {

    private static final Logger LOG = LoggerFactory.getLogger(EarthquakeRecordService.class);

    private EarthquakeRecordDao earthquakeRecordDao;

    private EarthquakeRecordListToResponseTransformer earthquakeRecordListToResponseTransformer;

    @Inject
    public EarthquakeRecordService(EarthquakeRecordDao earthquakeRecordDao,
                                   EarthquakeRecordListToResponseTransformer earthquakeRecordListToResponseTransformer) {
        this.earthquakeRecordDao = earthquakeRecordDao;
        this.earthquakeRecordListToResponseTransformer = earthquakeRecordListToResponseTransformer;
    }

    public EarthquakeRecordResponse getEarthquakesResponse(Double minimumMagnitude, Integer limit) {
        List<EarthquakeRecord> earthquakeRecords = earthquakeRecordDao.getEarthquakeRecords(minimumMagnitude, limit);
        return earthquakeRecordListToResponseTransformer.apply(earthquakeRecords);
    }

}
