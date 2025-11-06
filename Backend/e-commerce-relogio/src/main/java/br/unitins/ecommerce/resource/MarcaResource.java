package br.unitins.ecommerce.resource;

import br.unitins.ecommerce.dto.MarcaDTO;
import br.unitins.ecommerce.form.MarcaForm;
import br.unitins.ecommerce.service.MarcaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api/marcas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService marcaService;

    @GET
    public Response listarTodos() {
        List<MarcaDTO> marcas = marcaService.listarTodos();
        return Response.ok(marcas).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        MarcaDTO marca = marcaService.buscarPorId(id);
        return Response.ok(marca).build();
    }

    @POST
    public Response criarMarca(@Valid MarcaForm form) {
        MarcaDTO marca = marcaService.criarMarca(form);
        return Response.created(URI.create("/api/marcas/" + marca.id)).entity(marca).build();
    }

    @PUT
    @Path("{id}")
    public Response atualizarMarca(@PathParam("id") Long id, @Valid MarcaForm form) {
        MarcaDTO marca = marcaService.atualizarMarca(id, form);
        return Response.ok(marca).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletarMarca(@PathParam("id") Long id) {
        marcaService.deletarMarca(id);
        return Response.noContent().build();
    }
}
