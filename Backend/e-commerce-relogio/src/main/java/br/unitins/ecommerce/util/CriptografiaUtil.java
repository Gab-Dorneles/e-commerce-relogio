package br.unitins.ecommerce.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class CriptografiaUtil {

    /**
     * Gera um hash SHA-256 de uma senha (para uso simples, usar bcrypt em produção)
     */
    public static String hashSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] messageDigest = md.digest(senha.getBytes());
        return Base64.getEncoder().encodeToString(messageDigest);
    }

    /**
     * Gera um token seguro aleatório (para recuperação de senha, etc)
     */
    public static String gerarTokenSeguro() {
        SecureRandom random = new SecureRandom();
        byte[] values = new byte[32];
        random.nextBytes(values);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(values);
    }

    /**
     * Valida se uma senha corresponde ao hash (para uso simples)
     */
    public static boolean validarSenha(String senha, String hash) throws NoSuchAlgorithmException {
        String senhaHasheada = hashSenha(senha);
        return senhaHasheada.equals(hash);
    }
}
