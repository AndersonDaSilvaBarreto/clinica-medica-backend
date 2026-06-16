package com.topicos_especiais_1.clinica_medica.shared.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.shared.domain.exception.ValorInvalidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Valor implements Serializable {
    @Column(nullable = false, precision = 10, scale = 2)
    @EqualsAndHashCode.Include
    private BigDecimal value;

    private Valor(BigDecimal value) {
        this.value = value;
    }

    public static Valor of(BigDecimal value) {

        if(Objects.requireNonNull(value).compareTo(BigDecimal.ZERO) < 0) {
            throw ValorInvalidoException.valorNegativo();
        }
        return new Valor(value);
    }
}
