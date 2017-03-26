package com.intfinit.earthquakes.transformers;

import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import com.intfinit.earthquakes.model.EarthquakeRecordResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.intfinit.earthquakes.dao.entity.fixture.EarthquakeRecordFixture.buildEarthquakeDataList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EarthquakeRecordListToResponseTransformerTest {

    @Mock
    private EarthquakeRecordToModelTransformer recordToModelTransformer;

    @InjectMocks
    private EarthquakeRecordListToResponseTransformer earthquakeRecordListToResponseTransformer;

    @Test
    public void apply() throws Exception {
        List<EarthquakeRecord> earthquakeRecords = buildEarthquakeDataList(5);
        assertThat(earthquakeRecordListToResponseTransformer.apply(earthquakeRecords))
                .extracting(EarthquakeRecordResponse::getRecordCount)
                .containsExactly(earthquakeRecords.size());
        verify(recordToModelTransformer, times(earthquakeRecords.size())).apply(any(EarthquakeRecord.class));
    }

}