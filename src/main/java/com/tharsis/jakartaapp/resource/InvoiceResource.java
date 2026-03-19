package com.tharsis.jakartaapp.resource;

import com.tharsis.jakartaapp.dto.InvoiceDTO;
import com.tharsis.jakartaapp.service.InvoiceService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/invoices")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InvoiceResource {

    @Inject
    private InvoiceService service;

    @GET
    public List<InvoiceDTO> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public InvoiceDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(@Valid InvoiceDTO dto) {
        InvoiceDTO created = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public InvoiceDTO update(@PathParam("id") Long id, @Valid InvoiceDTO dto) {
        return service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
