package com.tharsis.jakartaapp.service;

import com.tharsis.jakartaapp.dto.InvoiceDTO;
import com.tharsis.jakartaapp.entity.Customer;
import com.tharsis.jakartaapp.entity.Invoice;
import com.tharsis.jakartaapp.repository.CustomerRepository;
import com.tharsis.jakartaapp.repository.InvoiceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service para Invoice - Business Logic Layer
 */
@ApplicationScoped
public class InvoiceService {

    @Inject
    private InvoiceRepository invoiceRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Transactional
    public InvoiceDTO create(InvoiceDTO dto) {
        // Validar se customer existe
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException(
                    "Customer não encontrado: " + dto.getCustomerId()));

        Invoice invoice = new Invoice();
        invoice.setAmount(dto.getAmount());
        invoice.setDescription(dto.getDescription());
        invoice.setDueDate(dto.getDueDate());
        invoice.setCustomer(customer);

        // O número é gerado automaticamente no @PrePersist da entidade

        invoice = invoiceRepository.save(invoice);

        return toDTO(invoice);
    }

    public Optional<InvoiceDTO> findById(Long id) {
        return invoiceRepository.findById(id)
                .map(this::toDTO);
    }

    public List<InvoiceDTO> findAll() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<InvoiceDTO> findByCustomerId(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<InvoiceDTO> update(Long id, InvoiceDTO dto) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    // Validar se customer existe (se foi alterado)
                    if (!invoice.getCustomer().getId().equals(dto.getCustomerId())) {
                        Customer customer = customerRepository.findById(dto.getCustomerId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                    "Customer não encontrado: " + dto.getCustomerId()));
                        invoice.setCustomer(customer);
                    }

                    invoice.setAmount(dto.getAmount());
                    invoice.setDescription(dto.getDescription());
                    invoice.setDueDate(dto.getDueDate());

                    Invoice updated = invoiceRepository.save(invoice);
                    return toDTO(updated);
                });
    }

    @Transactional
    public boolean delete(Long id) {
        if (invoiceRepository.existsById(id)) {
            invoiceRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Marcar invoice como paga
     */
    @Transactional
    public Optional<InvoiceDTO> markAsPaid(Long id) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoice.markAsPaid();
                    Invoice updated = invoiceRepository.save(invoice);
                    return toDTO(updated);
                });
    }

    /**
     * Marcar invoice como cancelada
     */
    @Transactional
    public Optional<InvoiceDTO> markAsCancelled(Long id) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoice.markAsCancelled();
                    Invoice updated = invoiceRepository.save(invoice);
                    return toDTO(updated);
                });
    }

    /**
     * Buscar invoices pendentes
     */
    public List<InvoiceDTO> findPending() {
        return invoiceRepository.findPending()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Buscar invoices vencidas
     */
    public List<InvoiceDTO> findOverdue() {
        return invoiceRepository.findOverdue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private InvoiceDTO toDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(invoice.getId());
        dto.setNumber(invoice.getNumber());
        dto.setAmount(invoice.getAmount());
        dto.setDescription(invoice.getDescription());
        dto.setDueDate(invoice.getDueDate());
        dto.setStatus(invoice.getStatus().name());
        dto.setCustomerId(invoice.getCustomer().getId());
        dto.setCustomerName(invoice.getCustomer().getName());
        return dto;
    }
}