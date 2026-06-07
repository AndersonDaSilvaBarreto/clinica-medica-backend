package com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.FormatoNomeInvalidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class Nome {
    @Column(name = "nome", nullable = false)
    @EqualsAndHashCode.Include
    private String value;

    private Nome(@NonNull String value) {
        String normalized = value.trim();
        Nome.validate(normalized);
        this.value = normalized;
    }
    public static Nome of(@NonNull String value) {
        return new Nome(value);
    }

    private static void validate(@NonNull String value) {
        if (value.isBlank()) {
          throw new FormatoNomeInvalidoException(FormatoNomeInvalidoException.VAZIO);
        }

        if (value.length() < 2 || value.length() > 150) {
            throw new FormatoNomeInvalidoException(FormatoNomeInvalidoException.TAMANHO);
        }
    }
    @Override
    public String toString() {
        return getValue();
    }
}
