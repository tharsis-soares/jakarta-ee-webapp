package com.tharsis.jakartaapp.resource;

import java.util.List;

import com.tharsis.jakartaapp.dto.CustomerDTO;
import com.tharsis.jakartaapp.service.CustomerService;

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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST Resource para Customer
 * Endpoints: /api/customers
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    private CustomerService customerService;

    /**
     * GET /api/customers
     * Listar todos os customers
     */
    @GET
    public Response findAll() {
        List<CustomerDTO> customers = customerService.findAll();
        return Response.ok(customers).build();
    }

    /**
     * GET /api/customers/{id}
     * Buscar customer por ID
     */
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return customerService.findById(id)
                .map(customer -> Response.ok(customer).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Customer não encontrado"))
                        .build());
    }

    /**
     * POST /api/customers
     * Criar novo customer
     */
    @POST
    public Response create(@Valid CustomerDTO dto) {
        try {
            CustomerDTO created = customerService.create(dto);
            return Response.status(Response.Status.CREATED)
                    .entity(created)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    /**
     * PUT /api/customers/{id}
     * Atualizar customer
     */
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CustomerDTO dto) {
        try {
            return customerService.update(id, dto)
                    .map(customer -> Response.ok(customer).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse("Customer não encontrado"))
                            .build());
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    /**
     * DELETE /api/customers/{id}
     * Deletar customer
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = customerService.delete(id);
        if (deleted) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse("Customer não encontrado"))
                .build();
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