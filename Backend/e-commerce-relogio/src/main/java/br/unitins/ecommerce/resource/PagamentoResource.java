package br.unitins.ecommerce.resource;

import br.unitins.ecommerce.dto.PagamentoDTO;
import br.unitins.ecommerce.form.PagamentoForm;
import br.unitins.ecommerce.service.PagamentoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/api/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    PagamentoService pagamentoService;

    @GET
    public Response listarTodos() {
        List<PagamentoDTO> pagamentos = pagamentoService.listarTodos();
        return Response.ok(pagamentos).build();
    }

    @GET
    @Path("{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        PagamentoDTO pagamento = pagamentoService.buscarPorId(id);
        return Response.ok(pagamento).build();
    }

    @GET
    @Path("/status/{status}")
    public Response buscarPorStatus(@PathParam("status") String status) {
        List<PagamentoDTO> pagamentos = pagamentoService.buscarPorStatus(status);
        return Response.ok(pagamentos).build();
    }

    @POST
    public Response criarPagamento(@Valid PagamentoForm form) {
        PagamentoDTO pagamento = pagamentoService.criarPagamento(form);
        return Response.created(URI.create("/api/pagamentos/" + pagamento.id)).entity(pagamento).build();
    }

    @PUT
    @Path("{id}/aprovar")
    public Response aprovarPagamento(@PathParam("id") Long id) {
        PagamentoDTO pagamento = pagamentoService.aprovarPagamento(id);
        return Response.ok(pagamento).build();
    }

    @PUT
    @Path("{id}/recusar")
    public Response recusarPagamento(@PathParam("id") Long id, Map<String, String> body) {
        String motivo = body.get("motivo");
        PagamentoDTO pagamento = pagamentoService.recusarPagamento(id, motivo);
        return Response.ok(pagamento).build();
    }

    @PUT
    @Path("{id}/reembolsar")
    public Response reembolsarPagamento(@PathParam("id") Long id) {
        PagamentoDTO pagamento = pagamentoService.reembolsarPagamento(id);
        return Response.ok(pagamento).build();
    }

    @DELETE
    @Path("{id}")
    public Response deletarPagamento(@PathParam("id") Long id) {
        pagamentoService.deletarPagamento(id);
        return Response.noContent().build();
    }
}
