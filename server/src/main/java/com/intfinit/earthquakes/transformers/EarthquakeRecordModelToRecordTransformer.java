package com.intfinit.earthquakes.transformers;

import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import com.intfinit.earthquakes.dao.entity.EarthquakeRecord.Builder;
import com.intfinit.earthquakes.model.EarthquakeRecordModel;

import java.util.function.Function;

public class EarthquakeRecordModelToRecordTransformer implements Function<EarthquakeRecordModel, EarthquakeRecord> {

    @Override
    public EarthquakeRecord apply(EarthquakeRecordModel earthquakeRecordModel) {
        return new Builder()
                .withDepth(earthquakeRecordModel.getDepth())
                .withEarthQuakeDatetime(earthquakeRecordModel.getEarthQuakeDatetime())
                .withLatitude(earthquakeRecordModel.getLatitude())
                .withLongitude(earthquakeRecordModel.getLongitude())
                .withMagnitude(earthquakeRecordModel.getMagnitude())
                .withRegion(earthquakeRecordModel.getRegion())
                .withSourceName(earthquakeRecordModel.getSourceName())
                .build();
    }
}
