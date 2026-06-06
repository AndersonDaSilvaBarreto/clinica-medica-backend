package com.topicos_especiais_1.clinica_medica.identidade.domain.service;

import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.FormatoSenhaInvalidoException;

import java.util.regex.Pattern;

public class SenhaValidator {
    private static final int TAMANHO_MINIMO = 8;
    private static final Pattern TEM_NUMERO = Pattern.compile(".*\\d.*");
    private static final Pattern TEM_MAIUSCULA = Pattern.compile(".*[A-Z].*");
    private static final Pattern TEM_MINUSCULA = Pattern.compile(".*[a-z].*");
    private static final Pattern TEM_ESPECIAL = Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{}].*");

    public static void validar(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new FormatoSenhaInvalidoException(FormatoSenhaInvalidoException.VAZIA);
        }
        if (senha.length() < TAMANHO_MINIMO) {
            throw new FormatoSenhaInvalidoException(FormatoSenhaInvalidoException.MUITO_CURTA);
        }
        if (!TEM_NUMERO.matcher(senha).matches()) {
            throw new FormatoSenhaInvalidoException(FormatoSenhaInvalidoException.SEM_NUMERO);
        }
        if (!TEM_MAIUSCULA.matcher(senha).matches()) {
            throw new FormatoSenhaInvalidoException(FormatoSenhaInvalidoException.SEM_MAIUSCULA);
        }
        if (!TEM_MINUSCULA.matcher(senha).matches()) {
            throw new FormatoSenhaInvalidoException(FormatoSenhaInvalidoException.SEM_MINUSCULA);
        }
        if (!TEM_ESPECIAL.matcher(senha).matches()) {
            throw new FormatoSenhaInvalidoException(FormatoSenhaInvalidoException.SEM_ESPECIAL);
        }
    }
}
