package com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject;

import br.com.caelum.stella.validation.CPFValidator;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.CPFInvalidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cpf implements Serializable {
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    @EqualsAndHashCode.Include
    private String value;
    private Cpf(String value) {
        var validator = new CPFValidator();
        if(!validator.invalidMessagesFor(value).isEmpty()) {
            throw CPFInvalidoException.cpfInvalido();
        }
        this.value = value;
    }
    public static Cpf of(String value) {
        return new Cpf(Objects.requireNonNull(value));
    }

    @Override
    public String toString() {
        return getValue();
    }
}
