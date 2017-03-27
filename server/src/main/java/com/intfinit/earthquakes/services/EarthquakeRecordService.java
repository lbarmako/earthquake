package com.intfinit.earthquakes.services;

import com.intfinit.earthquakes.dao.EarthquakeRecordDao;
import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import com.intfinit.earthquakes.model.EarthquakeRecordModel;
import com.intfinit.earthquakes.model.EarthquakeRecordResponse;
import com.intfinit.earthquakes.transformers.EarthquakeRecordListToResponseTransformer;
import com.intfinit.earthquakes.transformers.EarthquakeRecordModelToRecordTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.CREATED;


public class EarthquakeRecordService {

    private static final Logger LOG = LoggerFactory.getLogger(EarthquakeRecordService.class);

    private final EarthquakeRecordModelToRecordTransformer earthquakeRecordModelToRecordTransformer;
    private final EarthquakeRecordDao earthquakeRecordDao;
    private final EarthquakeRecordListToResponseTransformer earthquakeRecordListToResponseTransformer;

    @Inject
    public EarthquakeRecordService(EarthquakeRecordDao earthquakeRecordDao,
                                   EarthquakeRecordListToResponseTransformer earthquakeRecordListToResponseTransformer,
                                   EarthquakeRecordModelToRecordTransformer earthquakeRecordModelToRecordTransformer) {
        this.earthquakeRecordDao = earthquakeRecordDao;
        this.earthquakeRecordListToResponseTransformer = earthquakeRecordListToResponseTransformer;
        this.earthquakeRecordModelToRecordTransformer = earthquakeRecordModelToRecordTransformer;
    }

    public EarthquakeRecordResponse getEarthquakesResponse(Double minimumMagnitude, Integer limit) {
        List<EarthquakeRecord> earthquakeRecords = earthquakeRecordDao.getEarthquakeRecords(minimumMagnitude, limit);
        return earthquakeRecordListToResponseTransformer.apply(earthquakeRecords);
    }

    public Response addEarthquakeRecord(EarthquakeRecordModel earthquakeRecordModel) {
        EarthquakeRecord earthquakeRecord = earthquakeRecordModelToRecordTransformer.apply(earthquakeRecordModel);
        LOG.info("Processing earthquake record={}", earthquakeRecord);

        Optional<EarthquakeRecord> earthquakeRecordOptional = earthquakeRecordDao.findByNaturalId(earthquakeRecord);
        if (!earthquakeRecordOptional.isPresent()) {
            earthquakeRecordDao.save(earthquakeRecord);
            return Response.status(CREATED).build();
        } else {
            LOG.error("Processing earthquake record={}", earthquakeRecord);
            return Response.status(CONFLICT).entity(earthquakeRecordOptional.get()).build();
        }
    }
}
