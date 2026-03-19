package com.tharsis.jakartaapp.repository;

import com.tharsis.jakartaapp.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;

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
}
