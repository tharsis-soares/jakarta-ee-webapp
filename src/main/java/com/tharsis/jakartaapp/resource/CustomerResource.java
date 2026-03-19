package com.tharsis.jakartaapp.resource;

import com.tharsis.jakartaapp.dto.CustomerDTO;
import com.tharsis.jakartaapp.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;

@Path("/customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    private CustomerService service;

    @GET
    public List<CustomerDTO> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public CustomerDTO findById(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    public Response create(@Valid CustomerDTO dto) {
        CustomerDTO created = service.create(dto);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public CustomerDTO update(@PathParam("id") Long id, @Valid CustomerDTO dto) {
        return service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
