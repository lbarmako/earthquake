package com.intfinit.earthquakes.services;

import com.intfinit.earthquakes.dao.EarthquakeRecordDao;
import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import com.intfinit.earthquakes.model.EarthquakeRecordModel;
import com.intfinit.earthquakes.transformers.EarthquakeRecordListToResponseTransformer;
import com.intfinit.earthquakes.transformers.EarthquakeRecordModelToRecordTransformer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.CREATED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EarthquakeRecordServiceTest {

    @Mock
    private EarthquakeRecordModelToRecordTransformer earthquakeRecordModelToRecordTransformer;
    @Mock
    private EarthquakeRecordDao earthquakeRecordDao;
    @Mock
    private EarthquakeRecordListToResponseTransformer earthquakeRecordListToResponseTransformer;

    @Mock
    private EarthquakeRecord earthquakeRecord;
    @Mock
    private EarthquakeRecordModel earthquakeRecordModel;

    @InjectMocks
    private EarthquakeRecordService earthquakeRecordService;

    @Before
    public void setUp() throws Exception {
        when(earthquakeRecordModelToRecordTransformer.apply(eq(earthquakeRecordModel))).thenReturn(earthquakeRecord);
    }

    @Test
    public void addEarthquakeRecordWhenDoesNotExistInDBAddTheRecordAndReturn201Reponse() throws Exception {
        when(earthquakeRecordDao.findByNaturalId(eq(earthquakeRecord))).thenReturn(Optional.empty());

        assertThat(earthquakeRecordService.addEarthquakeRecord(earthquakeRecordModel))
                .isNotNull()
                .extracting(Response::getStatus)
                .containsExactly(CREATED.getStatusCode());
        verify(earthquakeRecordDao, times(1)).save(eq(earthquakeRecord));
    }

    @Test
    public void addEarthquakeRecordWhenExistsInDBReturn409ReponseAndEntityContainsExistingRecord() throws Exception {
        when(earthquakeRecordDao.findByNaturalId(eq(earthquakeRecord))).thenReturn(Optional.of(earthquakeRecord));

        Response response = earthquakeRecordService.addEarthquakeRecord(earthquakeRecordModel);
        assertThat(response)
                .isNotNull()
                .extracting(Response::getStatus)
                .containsExactly(CONFLICT.getStatusCode());
        assertThat((EarthquakeRecord) response.getEntity()).isEqualTo(earthquakeRecord);
        verify(earthquakeRecordDao, never()).save(eq(earthquakeRecord));
    }

}