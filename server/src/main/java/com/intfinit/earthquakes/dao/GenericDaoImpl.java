package com.intfinit.earthquakes.dao;

import com.google.common.base.Throwables;
import org.hibernate.exception.ConstraintViolationException;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {
    private static final long serialVersionUID = 2106368619991141352L;

    protected final Provider<EntityManager> emProvider;
    protected final Class<T> entityClass;

    protected GenericDaoImpl(Provider<EntityManager> emProvider, Class<T> entityClass) {
        this.emProvider = emProvider;
        this.entityClass = entityClass;
    }

    public EntityManager getEntityManager() {
        return emProvider.get();
    }

    static boolean isDuplicateEntry(Exception e) {
        return isConstraintViolationException(e) && Throwables.getRootCause(e).getMessage().startsWith("Duplicate entry ");
    }

    private static boolean isConstraintViolationException(Throwable e) {
        List<Throwable> causeChain = Throwables.getCausalChain(e);
        return causeChain.size() > 1 && causeChain.get(1) instanceof ConstraintViolationException;
    }
}
