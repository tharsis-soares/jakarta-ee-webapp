package com.tharsis.jakartaapp.service;

import com.tharsis.jakartaapp.dto.InvoiceDTO;
import com.tharsis.jakartaapp.entity.Customer;
import com.tharsis.jakartaapp.entity.Invoice;
import com.tharsis.jakartaapp.exception.BusinessException;
import com.tharsis.jakartaapp.repository.CustomerRepository;
import com.tharsis.jakartaapp.repository.InvoiceRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class InvoiceService {

    @Inject
    private InvoiceRepository repository;

    @Inject
    private CustomerRepository customerRepository;

    public List<InvoiceDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public InvoiceDTO findById(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new BusinessException("Invoice not found: " + id));
    }

    @Transactional
    public InvoiceDTO create(InvoiceDTO dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new BusinessException("Customer not found: " + dto.getCustomerId()));
        Invoice invoice = toEntity(dto);
        invoice.setCustomer(customer);
        invoice.setNumber("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        return toDTO(repository.save(invoice));
    }

    @Transactional
    public InvoiceDTO update(Long id, InvoiceDTO dto) {
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Invoice not found: " + id));
        invoice.setAmount(dto.getAmount());
        invoice.setDescription(dto.getDescription());
        invoice.setDueDate(dto.getDueDate());
        if (dto.getStatus() != null) {
            invoice.setStatus(Invoice.InvoiceStatus.valueOf(dto.getStatus()));
        }
        return toDTO(repository.save(invoice));
    }

    @Transactional
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new BusinessException("Invoice not found: " + id));
        repository.delete(id);
    }

    private InvoiceDTO toDTO(Invoice i) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(i.getId());
        dto.setNumber(i.getNumber());
        dto.setAmount(i.getAmount());
        dto.setDescription(i.getDescription());
        dto.setDueDate(i.getDueDate());
        dto.setStatus(i.getStatus().name());
        dto.setCustomerId(i.getCustomer().getId());
        return dto;
    }

    private Invoice toEntity(InvoiceDTO dto) {
        Invoice i = new Invoice();
        i.setAmount(dto.getAmount());
        i.setDescription(dto.getDescription());
        i.setDueDate(dto.getDueDate());
        return i;
    }
}
