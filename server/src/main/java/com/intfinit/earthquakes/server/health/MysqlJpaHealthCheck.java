package com.intfinit.earthquakes.server.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class MysqlJpaHealthCheck extends HealthCheck {

    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public MysqlJpaHealthCheck(Provider<EntityManager> entityManager) {
        this.entityManagerProvider = entityManager;
    }

    @Override
    @Transactional
    protected Result check() throws Exception {
        EntityManager entityManager = entityManagerProvider.get();
        entityManager.createNativeQuery("/* ping */ SELECT 1").getFirstResult();
        return Result.healthy();
    }
}
