package com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.shared.domain.exception.FormatoNomeInvalidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Nome implements Serializable {
    @Column(name = "nome", length = 150, nullable = false)
    @EqualsAndHashCode.Include
    private String value;

    private Nome(String value) {
        String normalized = Objects.requireNonNull(value).trim();
        Nome.validate(normalized);
        this.value = normalized;
    }
    public static Nome of(String value) {
        return new Nome(value);
    }

    private static void validate(String value) {
        Objects.requireNonNull(value);
        if (value.length() < 2 || value.length() > 150) {
            throw new FormatoNomeInvalidoException(FormatoNomeInvalidoException.TAMANHO);
        }
    }
    @Override
    public String toString() {
        return getValue();
    }
}
