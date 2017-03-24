package com.intfinit.earthquakes.dao;

import com.google.inject.persist.Transactional;
import com.intfinit.earthquakes.dao.entity.EarthquakeRecord;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.Optional;

public class EarthquakeRecordDaoImpl extends GenericDaoImpl<EarthquakeRecord, Long> implements EarthquakeRecordDao {

    private static final long serialVersionUID = 591072872061545776L;
    private static final Logger LOG = LoggerFactory.getLogger(EarthquakeRecordDaoImpl.class);

    @Inject
    public EarthquakeRecordDaoImpl(Provider<EntityManager> emProvider) {
        super(emProvider, EarthquakeRecord.class);
    }

    @Override
    @Transactional
    public void save(@Valid EarthquakeRecord earthquakeRecord) {
        EntityManager em = getEntityManager();
        em.persist(earthquakeRecord);
    }

    @Override
    @Transactional
    public Optional<EarthquakeRecord> findByNaturalId(EarthquakeRecord earthquakeRecord) {
        EntityManager em = getEntityManager();
        Session session = em.unwrap(Session.class);
        EarthquakeRecord loadedRecord = session.byNaturalId(EarthquakeRecord.class)
                .using("earthQuakeDatetime", earthquakeRecord.getEarthQuakeDatetime())
                .using("latitude", earthquakeRecord.getLatitude())
                .using("longitude", earthquakeRecord.getLongitude())
                .using("magnitude", earthquakeRecord.getMagnitude())
                .using("depth", earthquakeRecord.getDepth()).load();
        return Optional.ofNullable(loadedRecord);
    }
}
