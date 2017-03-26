package com.intfinit.earthquakes.transformers;

import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import com.intfinit.earthquakes.model.EarthquakeRecordModel;
import org.junit.Before;
import org.junit.Test;

import static com.intfinit.earthquakes.model.fixtures.EarthquakeRecordModelFixture.buildEarthquakeDataModel;
import static org.assertj.core.api.Assertions.assertThat;

public class EarthquakeRecordModelToRecordTransformerTest {

    private EarthquakeRecordModelToRecordTransformer earthquakeRecordModelToRecordTransformer;

    @Before
    public void setUp() throws Exception {
        earthquakeRecordModelToRecordTransformer = new EarthquakeRecordModelToRecordTransformer();
    }

    @Test
    public void apply() throws Exception {
        EarthquakeRecordModel earthquakeRecordModel = buildEarthquakeDataModel();
        assertThat(earthquakeRecordModelToRecordTransformer.apply(earthquakeRecordModel))
                .extracting(EarthquakeRecord::getSourceName,
                        EarthquakeRecord::getEarthQuakeDatetime,
                        EarthquakeRecord::getLatitude,
                        EarthquakeRecord::getLongitude,
                        EarthquakeRecord::getMagnitude,
                        EarthquakeRecord::getDepth,
                        EarthquakeRecord::getRegion)
                .containsExactly(earthquakeRecordModel.getSourceName(),
                        earthquakeRecordModel.getEarthQuakeDatetime(),
                        earthquakeRecordModel.getLatitude(),
                        earthquakeRecordModel.getLongitude(),
                        earthquakeRecordModel.getMagnitude(),
                        earthquakeRecordModel.getDepth(),
                        earthquakeRecordModel.getRegion());
    }

}