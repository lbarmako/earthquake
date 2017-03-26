package com.intfinit.earthquakes.dao;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.intfinit.earthquakes.ApplicationRule;
import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static com.intfinit.earthquakes.dao.entity.EarthquakeRecord.GET_ALL_EARTHQUAKE_RECORDS;
import static com.intfinit.earthquakes.dao.entity.fixture.EarthquakeRecordFixture.buildEarthquakeRecord;
import static com.intfinit.earthquakes.dao.entity.fixture.EarthquakeRecordFixture.buildEarthquakeDataList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class EarthquakeRecordDaoTest {

    @Rule
    public ApplicationRule ar = new ApplicationRule();

    @Inject
    private EarthquakeRecordDao earthquakeRecordDao;

    @Inject
    private EntityManager em;
    private EntityTransaction transaction;

    @Before
    public void setUp() {
        ar.getApplication().getInjector().injectMembers(this);
        transaction = em.getTransaction();
        transaction.begin();
    }

    private void cleanTable() {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM EarthquakeRecord").executeUpdate();
        em.getTransaction().commit();
    }

    @After
    public void tearDown() {
        transaction.rollback();
    }

    @Test
    public void saveEarthquakeRecord() {
        EarthquakeRecord earthquakeRecord = buildEarthquakeRecord();
        earthquakeRecordDao.save(earthquakeRecord);

        TypedQuery<EarthquakeRecord> namedQuery = em.createNamedQuery(GET_ALL_EARTHQUAKE_RECORDS, EarthquakeRecord.class);
        List<EarthquakeRecord> earthquakeRecords = namedQuery.getResultList();

        assertThat(earthquakeRecords).hasSize(1)
                .first()
                .extracting(EarthquakeRecord::getSourceName, EarthquakeRecord::getLatitude,
                        EarthquakeRecord::getLongitude, EarthquakeRecord::getMagnitude, EarthquakeRecord::getDepth,
                        EarthquakeRecord::getRegion, EarthquakeRecord::getEarthQuakeDatetime)
                .containsExactly(earthquakeRecord.getSourceName(), earthquakeRecord.getLatitude(),
                        earthquakeRecord.getLongitude(), earthquakeRecord.getMagnitude(), earthquakeRecord.getDepth(),
                        earthquakeRecord.getRegion(), earthquakeRecord.getEarthQuakeDatetime());
        assertThat(earthquakeRecords.get(0).getId()).isGreaterThan(0);
    }

    @Test
    @Transactional
    public void findByNaturalIdWhenRecordExistsReturnsOneRecord() {
        EarthquakeRecord earthquakeRecord = buildEarthquakeRecord();
        earthquakeRecordDao.save(earthquakeRecord);

        Optional<EarthquakeRecord> earthquakeRecordOptional = earthquakeRecordDao.findByNaturalId(earthquakeRecord);
        checkEarquakeRecord(earthquakeRecord, earthquakeRecordOptional.get());
    }

    @Test
    @Transactional
    public void findByNaturalIdWhenRecordDoesNotExistsReturnsNull() {
        EarthquakeRecord earthquakeRecord = buildEarthquakeRecord();

        Optional<EarthquakeRecord> earthquakeRecordOptional = earthquakeRecordDao.findByNaturalId(earthquakeRecord);
        assertThat(earthquakeRecordOptional).isEmpty();
    }

    @Test
    @Transactional
    public void duplicateInsertCausesPersistenceException() {
        EarthquakeRecord earthquakeRecord1 = buildEarthquakeRecord();
        earthquakeRecordDao.save(earthquakeRecord1);
        EarthquakeRecord earthquakeRecord2 = buildEarthquakeRecord();
        assertThatExceptionOfType(PersistenceException.class)
                .isThrownBy(() -> {
                    earthquakeRecordDao.save(earthquakeRecord2);
                });
    }

    @Test
    @Transactional
    public void getEarthquakeRecordsReturnsAllRecordsWhenPassedZeroMaxResults() {
        List<EarthquakeRecord> earthquakeRecords = buildEarthquakeDataList(10);
        earthquakeRecords.forEach(earthquakeRecord -> earthquakeRecordDao.save(earthquakeRecord));
        assertThat(earthquakeRecordDao.getEarthquakeRecords(0d, 0)).hasSize(earthquakeRecords.size());
    }

    @Test
    @Transactional
    public void getEarthquakeRecordsReturnsSpecifiedNumberOfRecordsWhenLimitIsBelowTotal() {
        List<EarthquakeRecord> earthquakeRecords = buildEarthquakeDataList(10);
        earthquakeRecords.forEach(earthquakeRecord -> earthquakeRecordDao.save(earthquakeRecord));
        assertThat(earthquakeRecordDao.getEarthquakeRecords(0d, 5)).hasSize(5);
    }

    @Test
    @Transactional
    public void getEarthquakeRecordsReturnsExpectedNumberOfRecordsWhenMinMagnitudeIsSet() {
        List<EarthquakeRecord> earthquakeRecords = buildEarthquakeDataList(10);
        final double minimumMagnitude = 9.3d;
        long expectedGreaterOrEqualsThenMinimum = earthquakeRecords.stream().filter(earthquakeRecord -> earthquakeRecord.getMagnitude() >= minimumMagnitude).count();
        earthquakeRecords.forEach(earthquakeRecord -> earthquakeRecordDao.save(earthquakeRecord));
        assertThat(earthquakeRecordDao.getEarthquakeRecords(minimumMagnitude, 0)).hasSize((int) expectedGreaterOrEqualsThenMinimum);
    }

    private void checkEarquakeRecord(EarthquakeRecord expectedRecord, EarthquakeRecord actualRecord) {
        assertThat(actualRecord)
                .extracting(EarthquakeRecord::getSourceName, EarthquakeRecord::getLatitude,
                        EarthquakeRecord::getLongitude, EarthquakeRecord::getMagnitude, EarthquakeRecord::getDepth,
                        EarthquakeRecord::getRegion, EarthquakeRecord::getEarthQuakeDatetime)
                .containsExactly(expectedRecord.getSourceName(), expectedRecord.getLatitude(),
                        expectedRecord.getLongitude(), expectedRecord.getMagnitude(), expectedRecord.getDepth(),
                        expectedRecord.getRegion(), expectedRecord.getEarthQuakeDatetime());
    }
}