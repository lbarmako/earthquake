package com.intfinit.earthquakes.model.fixtures;

import com.intfinit.earthquakes.model.EarthquakeRecordModel;
import com.intfinit.earthquakes.model.EarthquakeRecordModel.Builder;

import java.time.OffsetDateTime;

import static java.time.OffsetDateTime.of;
import static java.time.ZoneOffset.UTC;

public class EarthquakeRecordModelFixture {

    public static EarthquakeRecordModel buildEarthquakeDataModel() {
        return createEarthquakeDataModel("us",
                of(2011, 3, 31, 7, 15, 30, 0, UTC),
                38.9536d, 142.0174, 6.2d, 39.6d,
                "near the east coast of Honshu, Japan");
    }

    static EarthquakeRecordModel createEarthquakeDataModel(String sourceName, OffsetDateTime earthQuakeDatetime,
                                                           Double latitude, Double longitude, Double magnitude,
                                                           Double depth, String region) {
        return new Builder().withSourceName(sourceName).withEarthQuakeDatetime(earthQuakeDatetime).
                withLatitude(latitude).withLongitude(longitude).withMagnitude(magnitude).withDepth(depth).
                withRegion(region).build();
    }
}
