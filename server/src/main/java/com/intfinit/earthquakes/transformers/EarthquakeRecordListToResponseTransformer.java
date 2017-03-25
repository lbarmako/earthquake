package com.intfinit.earthquakes.transformers;

import com.google.inject.Inject;
import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import com.intfinit.earthquakes.model.EarthquakeRecordResponse;
import com.intfinit.earthquakes.model.EarthquakeRecordResponse.Builder;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EarthquakeRecordListToResponseTransformer implements Function<List<EarthquakeRecord>, EarthquakeRecordResponse> {

    @Inject
    private EarthquakeRecordToModelTransformer earthquakeRecordToModelTransformer;

    @Override
    public EarthquakeRecordResponse apply(List<EarthquakeRecord> earthquakeRecords) {
        return new Builder()
                .withRecordCount(earthquakeRecords.size())
                .withEarthquakes(earthquakeRecords.stream()
                        .map(earthquakeRecord -> earthquakeRecordToModelTransformer.apply(earthquakeRecord))
                        .collect(Collectors.toList()))
                .build();
    }
}
