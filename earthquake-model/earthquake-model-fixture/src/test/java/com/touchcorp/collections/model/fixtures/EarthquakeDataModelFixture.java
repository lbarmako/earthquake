package com.touchcorp.collections.model.fixtures;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.intfinit.earthquakes.model.EarthquakeDataModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.time.OffsetDateTime.of;
import static java.time.ZoneOffset.UTC;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class EarthquakeDataModelFixture {

    public static EarthquakeDataModel buildEarthquakeDataModel() {
        return createEarthquakeDataModel("us",
                of(2011, 3, 31, 7, 15, 30, 0, UTC),
                38.9536d, 142.0174, 6.2d, 39.6d,
                "near the east coast of Honshu, Japan");
    }

    static EarthquakeDataModel createEarthquakeDataModel(String sourceName, OffsetDateTime earthQuakeDatetime,
                                                         Double latitude, Double longitude, Double magnitude,
                                                         Double depth, String region) {
        return new EarthquakeDataModel(sourceName, earthQuakeDatetime, latitude, longitude, magnitude, depth, region);
    }
}
