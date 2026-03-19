package com.tharsis.jakartaapp.repository;

import com.tharsis.jakartaapp.entity.Invoice;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class InvoiceRepository {

    @PersistenceContext(unitName = "invoicePU")
    private EntityManager em;

    public List<Invoice> findAll() {
        return em.createQuery("SELECT i FROM Invoice i", Invoice.class).getResultList();
    }

    public Optional<Invoice> findById(Long id) {
        return Optional.ofNullable(em.find(Invoice.class, id));
    }

    public List<Invoice> findByCustomerId(Long customerId) {
        return em.createQuery("SELECT i FROM Invoice i WHERE i.customer.id = :customerId", Invoice.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public Invoice save(Invoice invoice) {
        if (invoice.getId() == null) {
            em.persist(invoice);
            return invoice;
        }
        return em.merge(invoice);
    }

    public void delete(Long id) {
        findById(id).ifPresent(em::remove);
    }
}
