package br.unitins.ecommerce.resource;

import br.unitins.ecommerce.dto.NotaDTO;
import br.unitins.ecommerce.form.NotaForm;
import br.unitins.ecommerce.service.NotaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api/notas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotaResource {

    @Inject
    NotaService notaService;

    @GET
    public Response listarTodos() {
        List<NotaDTO> notas = notaService.listarTodos();
        return Response.ok(notas).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        NotaDTO nota = notaService.buscarPorId(id);
        return Response.ok(nota).build();
    }

    @GET
    @Path("/numero/{numeroNota}")
    public Response buscarPorNumeroNota(@PathParam("numeroNota") String numeroNota) {
        NotaDTO nota = notaService.buscarPorNumeroNota(numeroNota);
        return Response.ok(nota).build();
    }

    @GET
    @Path("/pedido/{pedidoId}")
    public Response buscarPorPedidoId(@PathParam("pedidoId") Long pedidoId) {
        NotaDTO nota = notaService.buscarPorPedidoId(pedidoId);
        return Response.ok(nota).build();
    }

    @POST
    public Response criarNota(@Valid NotaForm form) {
        NotaDTO nota = notaService.criarNota(form);
        return Response.created(URI.create("/api/notas/" + nota.id)).entity(nota).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletarNota(@PathParam("id") Long id) {
        notaService.deletarNota(id);
        return Response.noContent().build();
    }
}
