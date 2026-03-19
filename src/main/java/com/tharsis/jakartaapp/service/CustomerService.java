package com.tharsis.jakartaapp.service;

import com.tharsis.jakartaapp.dto.CustomerDTO;
import com.tharsis.jakartaapp.entity.Customer;
import com.tharsis.jakartaapp.exception.BusinessException;
import com.tharsis.jakartaapp.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CustomerService {

    @Inject
    private CustomerRepository repository;

    public List<CustomerDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO findById(Long id) {
        return repository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new BusinessException("Customer not found: " + id));
    }

    @Transactional
    public CustomerDTO create(CustomerDTO dto) {
        Customer customer = toEntity(dto);
        return toDTO(repository.save(customer));
    }

    @Transactional
    public CustomerDTO update(Long id, CustomerDTO dto) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Customer not found: " + id));
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setTaxId(dto.getTaxId());
        return toDTO(repository.save(customer));
    }

    @Transactional
    public void delete(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new BusinessException("Customer not found: " + id));
        repository.delete(id);
    }

    private CustomerDTO toDTO(Customer c) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setEmail(c.getEmail());
        dto.setTaxId(c.getTaxId());
        return dto;
    }

    private Customer toEntity(CustomerDTO dto) {
        Customer c = new Customer();
        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setTaxId(dto.getTaxId());
        return c;
    }
}
