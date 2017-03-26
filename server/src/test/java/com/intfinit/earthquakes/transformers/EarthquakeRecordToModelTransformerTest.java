package com.intfinit.earthquakes.transformers;

import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import com.intfinit.earthquakes.model.EarthquakeRecordModel;
import org.junit.Before;
import org.junit.Test;

import static com.intfinit.earthquakes.dao.entity.fixture.EarthquakeRecordFixture.buildEarthquakeRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class EarthquakeRecordToModelTransformerTest {

    private EarthquakeRecordToModelTransformer earthquakeRecordToModelTransformer;

    @Before
    public void setUp() throws Exception {
        earthquakeRecordToModelTransformer = new EarthquakeRecordToModelTransformer();
    }

    @Test
    public void apply() throws Exception {
        EarthquakeRecord earthquakeRecord = buildEarthquakeRecord();
        assertThat(earthquakeRecordToModelTransformer.apply(earthquakeRecord))
                .extracting(EarthquakeRecordModel::getSourceName,
                        EarthquakeRecordModel::getEarthQuakeDatetime,
                        EarthquakeRecordModel::getLatitude,
                        EarthquakeRecordModel::getLongitude,
                        EarthquakeRecordModel::getMagnitude,
                        EarthquakeRecordModel::getDepth,
                        EarthquakeRecordModel::getRegion)
                .containsExactly(earthquakeRecord.getSourceName(),
                        earthquakeRecord.getEarthQuakeDatetime(),
                        earthquakeRecord.getLatitude(),
                        earthquakeRecord.getLongitude(),
                        earthquakeRecord.getMagnitude(),
                        earthquakeRecord.getDepth(),
                        earthquakeRecord.getRegion());
    }

}