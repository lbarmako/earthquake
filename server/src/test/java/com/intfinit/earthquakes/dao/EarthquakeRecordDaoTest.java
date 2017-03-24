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
import static com.intfinit.earthquakes.dao.entity.fixture.EarthquakeRecordFixture.buildEarthquakeData;
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
        EarthquakeRecord earthquakeRecord = buildEarthquakeData();
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
        EarthquakeRecord earthquakeRecord = buildEarthquakeData();
        earthquakeRecordDao.save(earthquakeRecord);

        Optional<EarthquakeRecord> earthquakeRecordOptional = earthquakeRecordDao.findByNaturalId(earthquakeRecord);
        checkEarquakeRecord(earthquakeRecord, earthquakeRecordOptional.get());
    }

    @Test
    @Transactional
    public void findByNaturalIdWhenRecordDoesNotExistsReturnsNull() {
        EarthquakeRecord earthquakeRecord = buildEarthquakeData();

        Optional<EarthquakeRecord> earthquakeRecordOptional = earthquakeRecordDao.findByNaturalId(earthquakeRecord);
        assertThat(earthquakeRecordOptional).isEmpty();
    }

    @Test
    @Transactional
    public void duplicateInsertCausesPersistenceException() {
        EarthquakeRecord earthquakeRecord1 = buildEarthquakeData();
        earthquakeRecordDao.save(earthquakeRecord1);
        EarthquakeRecord earthquakeRecord2 = buildEarthquakeData();
        assertThatExceptionOfType(PersistenceException.class)
                .isThrownBy(() -> {
                    earthquakeRecordDao.save(earthquakeRecord2);
                });
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