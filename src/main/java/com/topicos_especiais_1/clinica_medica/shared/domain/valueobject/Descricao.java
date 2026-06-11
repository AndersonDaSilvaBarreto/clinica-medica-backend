package com.topicos_especiais_1.clinica_medica.shared.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.shared.domain.exception.DescricaoInvalidaException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Descricao implements Serializable {
    @Column(name = "descricao", length = 500)
    private String value;

    private Descricao(@NonNull String value) {
        if(value.isBlank() || value.length() < 15 || value.length() >500 ) {
            throw DescricaoInvalidaException.descricaoInvalida();
        }
    }

    public static Descricao of(@NonNull String value) {
        return new Descricao(value);
    }

    @Override
    public String toString() {
        return getValue();
    }
}
