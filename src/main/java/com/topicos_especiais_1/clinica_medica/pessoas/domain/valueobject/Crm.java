package com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.exception.CrmInvalidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Crm implements Serializable {
    @Column(name = "crm", nullable = false, unique = true, length = 20)
    @EqualsAndHashCode.Include
    private String value;

    private Crm(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static Crm of(String value) {
        crmValidator(value);
        return new Crm(value);

    }

    private static final Pattern PATTERN =
            Pattern.compile("^\\d{1,6}[A-Z]{2}$");

    public static void crmValidator(String crm) {

        if(crm == null) throw CrmInvalidoException.crmInvalido();
        crm = crm.replace("-", "")
                .replace("/", "")
                .trim()
                .toUpperCase();
        if(PATTERN.matcher(crm).matches()) throw CrmInvalidoException.crmInvalido();
    }

    @Override
    public String toString() {
        return getValue();
    }
}
