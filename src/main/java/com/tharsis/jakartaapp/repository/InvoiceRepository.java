package com.tharsis.jakartaapp.repository;

import java.util.List;
import java.util.Optional;

import com.tharsis.jakartaapp.entity.Invoice;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

/**
 * Repository para Invoice
 */
@ApplicationScoped
public class InvoiceRepository {

    @PersistenceContext(unitName = "invoicePU")
    private EntityManager em;

    public List<Invoice> findAll() {
        return em.createQuery("SELECT i FROM Invoice i", Invoice.class)
                .getResultList();
    }

    public Optional<Invoice> findById(Long id) {
        return Optional.ofNullable(em.find(Invoice.class, id));
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

    public void deleteById(Long id) {
        findById(id).ifPresent(em::remove);
    }

    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    /**
     * Buscar todas as invoices de um customer
     */
    public List<Invoice> findByCustomerId(Long customerId) {
        TypedQuery<Invoice> query = em.createQuery(
            "SELECT i FROM Invoice i WHERE i.customer.id = :customerId",
            Invoice.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    /**
     * Buscar invoice por número
     */
    public Optional<Invoice> findByNumber(String number) {
        TypedQuery<Invoice> query = em.createQuery(
            "SELECT i FROM Invoice i WHERE i.number = :number",
            Invoice.class);
        query.setParameter("number", number);
        return query.getResultList().stream().findFirst();
    }

    /**
     * Verificar se número já existe
     */
    public boolean existsByNumber(String number) {
        return findByNumber(number).isPresent();
    }

    /**
     * Buscar invoices pendentes
     */
    public List<Invoice> findPending() {
        TypedQuery<Invoice> query = em.createQuery(
            "SELECT i FROM Invoice i WHERE i.status = 'PENDING'",
            Invoice.class);
        return query.getResultList();
    }

    /**
     * Buscar invoices vencidas
     */
    public List<Invoice> findOverdue() {
        TypedQuery<Invoice> query = em.createQuery(
            "SELECT i FROM Invoice i WHERE i.status = 'OVERDUE'",
            Invoice.class);
        return query.getResultList();
    }
}