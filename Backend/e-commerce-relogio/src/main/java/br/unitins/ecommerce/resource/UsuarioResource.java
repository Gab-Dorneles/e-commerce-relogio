package br.unitins.ecommerce.resource;

import br.unitins.ecommerce.dto.UsuarioDTO;
import br.unitins.ecommerce.form.UsuarioForm;
import br.unitins.ecommerce.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @GET
    public Response listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();
        return Response.ok(usuarios).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        UsuarioDTO usuario = usuarioService.buscarPorId(id);
        return Response.ok(usuario).build();
    }

    @POST
    public Response criarUsuario(@Valid UsuarioForm form) {
        UsuarioDTO usuario = usuarioService.criarUsuario(form);
        return Response.created(URI.create("/api/usuarios/" + usuario.id)).entity(usuario).build();
    }

    @PUT
    @Path("{id}")
    public Response atualizarUsuario(@PathParam("id") Long id, @Valid UsuarioForm form) {
        UsuarioDTO usuario = usuarioService.atualizarUsuario(id, form);
        return Response.ok(usuario).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletarUsuario(@PathParam("id") Long id) {
        usuarioService.deletarUsuario(id);
        return Response.noContent().build();
    }
}
