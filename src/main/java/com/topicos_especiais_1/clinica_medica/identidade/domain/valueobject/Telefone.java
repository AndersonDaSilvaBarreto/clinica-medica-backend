package com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.FormatoTelefoneInvalidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Telefone implements Serializable {
    private static final Pattern APENAS_DIGITOS = Pattern.compile("\\d{11}");
    @Column(name = "telefone", length = 11)
    @EqualsAndHashCode.Include
    private String value;

    private Telefone(String value) {
        this.value = value;
    }
    public static Telefone of(String value) {
        Objects.requireNonNull(value);
        String normalized = value.trim().replaceAll("[\\s()\\-+]", "");
        if(normalized.isBlank()) {
            throw new FormatoTelefoneInvalidoException(FormatoTelefoneInvalidoException.VAZIO);
        }
        if(!APENAS_DIGITOS.matcher(normalized).matches()) {
            throw new FormatoTelefoneInvalidoException(FormatoTelefoneInvalidoException.INVALIDO);
        }
        return new Telefone(normalized);
    }
    @Override
    public String toString() {
        return getValue();
    }

}
