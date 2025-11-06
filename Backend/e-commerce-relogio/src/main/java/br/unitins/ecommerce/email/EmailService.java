package br.unitins.ecommerce.email;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EmailService {

    @Inject
    Mailer mailer;

    public void enviarEmailConfirmacaoPedido(String email, String numeroPedido, String cliente) {
        String assunto = "Pedido Confirmado - " + numeroPedido;
        String corpo = "Olá " + cliente + ",\n\n" +
                "Seu pedido " + numeroPedido + " foi confirmado com sucesso!\n\n" +
                "Obrigado por sua compra.\n\n" +
                "Atenciosamente,\n" +
                "Equipe Relógios";

        enviarEmail(email, assunto, corpo);
    }

    public void enviarEmailRecuperacaoSenha(String email, String token) {
        String assunto = "Recuperação de Senha";
        String corpo = "Clique no link abaixo para recuperar sua senha:\n\n" +
                "http://localhost:4200/recuperar-senha?token=" + token + "\n\n" +
                "Este link expira em 24 horas.";

        enviarEmail(email, assunto, corpo);
    }

    public void enviarEmailNotificacaoPagamento(String email, String numeroPedido, String status) {
        String assunto = "Notificação de Pagamento - " + numeroPedido;
        String corpo = "Seu pagamento foi " + status + " com sucesso!\n\n" +
                "Pedido: " + numeroPedido + "\n\n" +
                "Obrigado por sua confiança.";

        enviarEmail(email, assunto, corpo);
    }

    private void enviarEmail(String para, String assunto, String corpo) {
        mailer.send(
            Mail.withText(para, assunto, corpo)
        );
    }
}
