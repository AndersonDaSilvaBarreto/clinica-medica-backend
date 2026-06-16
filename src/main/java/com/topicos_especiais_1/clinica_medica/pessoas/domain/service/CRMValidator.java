package com.topicos_especiais_1.clinica_medica.pessoas.domain.service;

import java.util.regex.Pattern;

public class CRMValidator {
    private static final Pattern PATTERN =
            Pattern.compile("^\\d{1,6}[A-Z]{2}$");

    public static boolean isValid(String crm) {
        if (crm == null) {
            return false;
        }

        crm = crm.replace("-", "")
                .replace("/", "")
                .trim()
                .toUpperCase();

        return PATTERN.matcher(crm).matches();
    }
}
