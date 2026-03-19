package com.tharsis.jakartaapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para Invoice
 * Usado para transferência de dados na API REST
 */
public class InvoiceDTO {

    private Long id;

    private String number;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal amount;

    @Size(max = 500, message = "Descrição não pode exceder 500 caracteres")
    private String description;

    @NotNull(message = "Data de vencimento é obrigatória")
    @Future(message = "Data de vencimento deve ser no futuro")
    private LocalDate dueDate;

    private String status;

    @NotNull(message = "Cliente é obrigatório")
    private Long customerId;

    private String customerName;

    // Constructors
    public InvoiceDTO() {}

    public InvoiceDTO(Long id, String number, BigDecimal amount, String description,
                      LocalDate dueDate, String status, Long customerId) {
        this.id = id;
        this.number = number;
        this.amount = amount;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.customerId = customerId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}