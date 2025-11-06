package br.unitins.ecommerce.resource;

import br.unitins.ecommerce.dto.PedidoDTO;
import br.unitins.ecommerce.form.PedidoForm;
import br.unitins.ecommerce.service.PedidoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/api/pedidos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    @GET
    public Response listarTodos() {
        List<PedidoDTO> pedidos = pedidoService.listarTodos();
        return Response.ok(pedidos).build();
    }

    @GET
    @Path("/usuario/{usuarioId}")
    public Response listarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        List<PedidoDTO> pedidos = pedidoService.listarPorUsuario(usuarioId);
        return Response.ok(pedidos).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        PedidoDTO pedido = pedidoService.buscarPorId(id);
        return Response.ok(pedido).build();
    }

    @POST
    public Response criarPedido(@Valid PedidoForm form) {
        PedidoDTO pedido = pedidoService.criarPedido(form);
        return Response.created(URI.create("/api/pedidos/" + pedido.id)).entity(pedido).build();
    }

    @PUT
    @Path("{id}/status")
    public Response atualizarStatus(@PathParam("id") Long id, Map<String, String> body) {
        String novoStatus = body.get("status");
        PedidoDTO pedido = pedidoService.atualizarStatusPedido(id, novoStatus);
        return Response.ok(pedido).build();
    }

    @PUT
    @Path("{id}/cancelar")
    public Response cancelarPedido(@PathParam("id") Long id) {
        pedidoService.cancelarPedido(id);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public Response deletarPedido(@PathParam("id") Long id) {
        pedidoService.deletarPedido(id);
        return Response.noContent().build();
    }
}
