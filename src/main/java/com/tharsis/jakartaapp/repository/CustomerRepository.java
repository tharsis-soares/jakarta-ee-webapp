package com.tharsis.jakartaapp.repository;

import java.util.List;
import java.util.Optional;

import com.tharsis.jakartaapp.entity.Customer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class CustomerRepository {

    @PersistenceContext(unitName = "invoicePU")
    private EntityManager em;

    public List<Customer> findAll() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(em.find(Customer.class, id));
    }

    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            em.persist(customer);
            return customer;
        }
        return em.merge(customer);
    }

    public void delete(Long id) {
        findById(id).ifPresent(em::remove);
    }

    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    public Optional<Customer> findByEmail(String email) {
        TypedQuery<Customer> query = em.createQuery(
            "SELECT c FROM Customer c WHERE c.email = :email", Customer.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

    public Optional<Customer> findByTaxId(String taxId) {
        TypedQuery<Customer> query = em.createQuery(
            "SELECT c FROM Customer c WHERE c.taxId = :taxId", Customer.class);
        query.setParameter("taxId", taxId);
        return query.getResultList().stream().findFirst();
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    public boolean existsByTaxId(String taxId) {
        return findByTaxId(taxId).isPresent();
    }
}