package br.unitins.ecommerce.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatadorUtil {

    private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    /**
     * Formata uma data e hora no padrão brasileiro
     */
    public static String formatarDataHora(LocalDateTime dataHora) {
        if (dataHora == null) return "";
        return dataHora.format(DT_FORMATTER);
    }

    /**
     * Formata uma data no padrão brasileiro
     */
    public static String formatarData(LocalDateTime data) {
        if (data == null) return "";
        return data.format(DATE_FORMATTER);
    }

    /**
     * Formata um valor monetário em Real brasileiro
     */
    public static String formatarMoeda(BigDecimal valor) {
        if (valor == null) return "";
        return CURRENCY_FORMATTER.format(valor);
    }

    /**
     * Remove caracteres especiais de um CEP (transforma xxxxx-xxx em xxxxxxxx)
     */
    public static String formatarCEP(String cep) {
        if (cep == null) return "";
        return cep.replaceAll("[^0-9]", "");
    }

    /**
     * Valida e formata um email
     */
    public static String formatarEmail(String email) {
        if (email == null) return "";
        return email.trim().toLowerCase();
    }

    /**
     * Gera um número de rastreamento único
     */
    public static String gerarNumeroRastreamento() {
        return "TRK-" + System.currentTimeMillis();
    }
}
