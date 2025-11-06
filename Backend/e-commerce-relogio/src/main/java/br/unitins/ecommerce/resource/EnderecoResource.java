package br.unitins.ecommerce.resource;

import br.unitins.ecommerce.dto.EnderecoDTO;
import br.unitins.ecommerce.form.EnderecoForm;
import br.unitins.ecommerce.service.EnderecoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api/enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService enderecoService;

    @GET
    public Response listarTodos() {
        List<EnderecoDTO> enderecos = enderecoService.listarTodos();
        return Response.ok(enderecos).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        EnderecoDTO endereco = enderecoService.buscarPorId(id);
        return Response.ok(endereco).build();
    }

    @POST
    public Response criarEndereco(@Valid EnderecoForm form) {
        EnderecoDTO endereco = enderecoService.criarEndereco(form);
        return Response.created(URI.create("/api/enderecos/" + endereco.id)).entity(endereco).build();
    }

    @PUT
    @Path("{id}")
    public Response atualizarEndereco(@PathParam("id") Long id, @Valid EnderecoForm form) {
        EnderecoDTO endereco = enderecoService.atualizarEndereco(id, form);
        return Response.ok(endereco).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletarEndereco(@PathParam("id") Long id) {
        enderecoService.deletarEndereco(id);
        return Response.noContent().build();
    }
}
