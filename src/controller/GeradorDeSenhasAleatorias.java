package controller;

import java.security.SecureRandom;

/**
 *
 * @author helio
 */
public class GeradorDeSenhasAleatorias {
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?";

    public String gerarSenhaAleatoria() {
        SecureRandom random = new SecureRandom();
        int tamanhoMinimo = 8;
        int tamanhoMaximo = 12;
        int tamanhoSenha = random.nextInt(tamanhoMaximo - tamanhoMinimo + 1) + tamanhoMinimo;

        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < tamanhoSenha; i++) {
            int indiceAleatorio = random.nextInt(CARACTERES.length());
            char caractereAleatorio = CARACTERES.charAt(indiceAleatorio);
            senha.append(caractereAleatorio);
        }

        return senha.toString();
    }

}





