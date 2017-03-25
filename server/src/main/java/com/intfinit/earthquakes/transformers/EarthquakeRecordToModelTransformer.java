package com.intfinit.earthquakes.transformers;

import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import com.intfinit.earthquakes.model.EarthquakeRecordModel;
import com.intfinit.earthquakes.model.EarthquakeRecordModel.Builder;

import java.util.function.Function;

public class EarthquakeRecordToModelTransformer implements Function<EarthquakeRecord, EarthquakeRecordModel> {

    @Override
    public EarthquakeRecordModel apply(EarthquakeRecord earthquakeRecord) {
        return new Builder()
                .withDepth(earthquakeRecord.getDepth())
                .withEarthQuakeDatetime(earthquakeRecord.getEarthQuakeDatetime())
                .withLatitude(earthquakeRecord.getLatitude())
                .withLongitude(earthquakeRecord.getLongitude())
                .withMagnitude(earthquakeRecord.getMagnitude())
                .withRegion(earthquakeRecord.getRegion())
                .withSourceName(earthquakeRecord.getSourceName())
                .build();
    }
}
