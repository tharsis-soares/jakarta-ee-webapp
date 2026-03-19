package com.tharsis.jakartaapp.repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;

/**
 * Repository genérico para operações CRUD
 */
public abstract class GenericRepository<T, ID> {

    @PersistenceContext
    protected EntityManager em;

    private final Class<T> entityClass;

    protected GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T save(T entity) {
        if (em.contains(entity)) {
            return em.merge(entity);
        } else {
            em.persist(entity);
            return entity;
        }
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    public List<T> findAll() {
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(entityClass);
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    public void delete(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public void deleteById(ID id) {
        findById(id).ifPresent(this::delete);
    }

    public long count() {
        CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
        cq.select(em.getCriteriaBuilder().count(cq.from(entityClass)));
        return em.createQuery(cq).getSingleResult();
    }

    public boolean existsById(ID id) {
        return findById(id).isPresent();
    }
}