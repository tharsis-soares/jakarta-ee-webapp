package com.tharsis.jakartaapp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tharsis.jakartaapp.dto.CustomerDTO;
import com.tharsis.jakartaapp.entity.Customer;
import com.tharsis.jakartaapp.repository.CustomerRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

/**
 * Service para Customer - Business Logic Layer
 */
@ApplicationScoped
public class CustomerService {

    @Inject
    private CustomerRepository customerRepository;

    @Transactional
    public CustomerDTO create(CustomerDTO dto) {
        // Validar se email já existe
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + dto.getEmail());
        }

        // Validar se taxId já existe
        if (customerRepository.existsByTaxId(dto.getTaxId())) {
            throw new IllegalArgumentException("CPF/CNPJ já cadastrado: " + dto.getTaxId());
        }

        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setTaxId(dto.getTaxId());

        customer = customerRepository.save(customer);

        return toDTO(customer);
    }

    public Optional<CustomerDTO> findById(Long id) {
        return customerRepository.findById(id)
                .map(this::toDTO);
    }

    public List<CustomerDTO> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<CustomerDTO> update(Long id, CustomerDTO dto) {
        return customerRepository.findById(id)
                .map(customer -> {
                    // Validar email se foi alterado
                    if (!customer.getEmail().equals(dto.getEmail())
                        && customerRepository.existsByEmail(dto.getEmail())) {
                        throw new IllegalArgumentException("Email já cadastrado: " + dto.getEmail());
                    }

                    // Validar taxId se foi alterado
                    if (!customer.getTaxId().equals(dto.getTaxId())
                        && customerRepository.existsByTaxId(dto.getTaxId())) {
                        throw new IllegalArgumentException("CPF/CNPJ já cadastrado: " + dto.getTaxId());
                    }

                    customer.setName(dto.getName());
                    customer.setEmail(dto.getEmail());
                    customer.setTaxId(dto.getTaxId());

                    Customer updated = customerRepository.save(customer);
                    return toDTO(updated);
                });
    }

    @Transactional
    public boolean delete(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setTaxId(customer.getTaxId());
        return dto;
    }
}