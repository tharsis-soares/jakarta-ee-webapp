package com.tharsis.jakartaapp.resource;

import java.util.List;

import com.tharsis.jakartaapp.dto.InvoiceDTO;
import com.tharsis.jakartaapp.service.InvoiceService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Resource para Invoice
 * Endpoints: /api/invoices
 */
@Path("/invoices")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InvoiceResource {

    @Inject
    private InvoiceService invoiceService;

    /**
     * GET /api/invoices
     * Listar todas as invoices
     */
    @GET
    public Response findAll(@QueryParam("customerId") Long customerId) {
        List<InvoiceDTO> invoices;

        if (customerId != null) {
            invoices = invoiceService.findByCustomerId(customerId);
        } else {
            invoices = invoiceService.findAll();
        }

        return Response.ok(invoices).build();
    }

    /**
     * GET /api/invoices/pending
     * Listar invoices pendentes
     */
    @GET
    @Path("/pending")
    public Response findPending() {
        List<InvoiceDTO> invoices = invoiceService.findPending();
        return Response.ok(invoices).build();
    }

    /**
     * GET /api/invoices/overdue
     * Listar invoices vencidas
     */
    @GET
    @Path("/overdue")
    public Response findOverdue() {
        List<InvoiceDTO> invoices = invoiceService.findOverdue();
        return Response.ok(invoices).build();
    }

    /**
     * GET /api/invoices/{id}
     * Buscar invoice por ID
     */
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return invoiceService.findById(id)
                .map(invoice -> Response.ok(invoice).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Invoice não encontrada"))
                        .build());
    }

    /**
     * POST /api/invoices
     * Criar nova invoice
     */
    @POST
    public Response create(@Valid InvoiceDTO dto) {
        try {
            InvoiceDTO created = invoiceService.create(dto);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    /**
     * PUT /api/invoices/{id}
     * Atualizar invoice
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid InvoiceDTO dto) {
        try {
            return invoiceService.update(id, dto)
                    .map(invoice -> Response.ok(invoice).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Invoice não encontrada"))
                            .build());
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    /**
     * DELETE /api/invoices/{id}
     * Deletar invoice
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = invoiceService.delete(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse("Invoice não encontrada"))
                .build();
    }

    /**
     * POST /api/invoices/{id}/pay
     * Marcar invoice como paga
     */
    @POST
    @Path("/{id}/pay")
    public Response markAsPaid(@PathParam("id") Long id) {
        return invoiceService.markAsPaid(id)
                .map(invoice -> Response.ok(invoice).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Invoice não encontrada"))
                        .build());
    }

    /**
     * POST /api/invoices/{id}/cancel
     * Marcar invoice como cancelada
     */
    @POST
    @Path("/{id}/cancel")
    public Response markAsCancelled(@PathParam("id") Long id) {
        return invoiceService.markAsCancelled(id)
                .map(invoice -> Response.ok(invoice).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Invoice não encontrada"))
                        .build());
    }

    // Classe interna para respostas de erro
    public static class ErrorResponse {
        private String message;

        public ErrorResponse() {}

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}