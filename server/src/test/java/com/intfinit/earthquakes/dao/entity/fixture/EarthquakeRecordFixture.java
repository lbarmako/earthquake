package com.intfinit.earthquakes.dao.entity.fixture;

import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import com.intfinit.earthquakes.model.EarthquakeDataModel;
import com.touchcorp.collections.model.fixtures.EarthquakeDataModelFixture;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class EarthquakeRecordFixture {

    public static List<EarthquakeRecord> buildEarthquakeDataList(int numberOfRecords) {
        List<EarthquakeRecord> earthquakeRecordList = newArrayList();
        EarthquakeRecord earthquakeRecord;
        for (long recordCount = 0; recordCount < numberOfRecords; recordCount++) {
            earthquakeRecord = buildEarthquakeData();
            earthquakeRecord.setId(recordCount);
            earthquakeRecordList.add(earthquakeRecord);
        }
        return earthquakeRecordList;
    }

    public static EarthquakeRecord buildEarthquakeData() {
        return createEarthquakeData();
    }

    private static EarthquakeRecord createEarthquakeData() {
        EarthquakeDataModel earthquakeDataModel = EarthquakeDataModelFixture.buildEarthquakeDataModel();
        EarthquakeRecord earthquakeRecord = new EarthquakeRecord();
        earthquakeRecord.setEarthQuakeDatetime(earthquakeDataModel.getEarthQuakeDatetime());
        earthquakeRecord.setSourceName(earthquakeDataModel.getSourceName());
        earthquakeRecord.setLatitude(earthquakeDataModel.getLatitude());
        earthquakeRecord.setLongitude(earthquakeDataModel.getLongitude());
        earthquakeRecord.setMagnitude(earthquakeDataModel.getMagnitude());
        earthquakeRecord.setDepth(earthquakeDataModel.getDepth());
        earthquakeRecord.setRegion(earthquakeDataModel.getRegion());
        return earthquakeRecord;
    }
}

