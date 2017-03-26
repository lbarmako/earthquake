package com.intfinit.earthquakes.dao.entity.fixture;

import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import com.intfinit.earthquakes.dao.entity.EarthquakeRecord.Builder;
import com.intfinit.earthquakes.model.EarthquakeRecordModel;
import com.intfinit.earthquakes.model.fixtures.EarthquakeRecordModelFixture;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class EarthquakeRecordFixture {

    public static List<EarthquakeRecord> buildEarthquakeDataList(int numberOfRecords) {
        List<EarthquakeRecord> earthquakeRecordList = newArrayList();
        EarthquakeRecord earthquakeRecord;
        for (long recordCount = 0; recordCount < numberOfRecords; recordCount++) {
            earthquakeRecord = buildEarthquakeRecord();
            earthquakeRecord.setMagnitude(earthquakeRecord.getMagnitude() + recordCount);
            earthquakeRecordList.add(earthquakeRecord);
        }
        return earthquakeRecordList;
    }

    public static EarthquakeRecord buildEarthquakeRecord() {
        return createEarthquakeRecordFromModel();
    }

    private static EarthquakeRecord createEarthquakeRecordFromModel() {
        EarthquakeRecordModel earthquakeRecordModel = EarthquakeRecordModelFixture.buildEarthquakeDataModel();
        return new Builder()
                .withDepth(earthquakeRecordModel.getDepth())
                .withEarthQuakeDatetime(earthquakeRecordModel.getEarthQuakeDatetime())
                .withSourceName(earthquakeRecordModel.getSourceName())
                .withLatitude(earthquakeRecordModel.getLatitude())
                .withLongitude(earthquakeRecordModel.getLongitude())
                .withMagnitude(earthquakeRecordModel.getMagnitude())
                .withRegion(earthquakeRecordModel.getRegion())
                .build();
    }
}

