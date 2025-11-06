package br.unitins.ecommerce.resource;

import br.unitins.ecommerce.dto.FabricanteDTO;
import br.unitins.ecommerce.form.FabricanteForm;
import br.unitins.ecommerce.service.FabricanteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api/fabricantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    @Inject
    FabricanteService fabricanteService;

    @GET
    public Response listarTodos() {
        List<FabricanteDTO> fabricantes = fabricanteService.listarTodos();
        return Response.ok(fabricantes).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        FabricanteDTO fabricante = fabricanteService.buscarPorId(id);
        return Response.ok(fabricante).build();
    }

    @POST
    public Response criarFabricante(@Valid FabricanteForm form) {
        FabricanteDTO fabricante = fabricanteService.criarFabricante(form);
        return Response.created(URI.create("/api/fabricantes/" + fabricante.id)).entity(fabricante).build();
    }

    @PUT
    @Path("{id}")
    public Response atualizarFabricante(@PathParam("id") Long id, @Valid FabricanteForm form) {
        FabricanteDTO fabricante = fabricanteService.atualizarFabricante(id, form);
        return Response.ok(fabricante).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletarFabricante(@PathParam("id") Long id) {
        fabricanteService.deletarFabricante(id);
        return Response.noContent().build();
    }
}
