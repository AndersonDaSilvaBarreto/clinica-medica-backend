package com.topicos_especiais_1.clinica_medica.shared.domain.valueobject;

import br.com.caelum.stella.validation.CPFValidator;
import com.topicos_especiais_1.clinica_medica.shared.exception.CPFInvalidoException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CPF {
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    @EqualsAndHashCode.Include
    private String value;
    private CPF(@NonNull String value) {
        var validator = new CPFValidator();
        if(!validator.invalidMessagesFor(value).isEmpty()) {
            throw CPFInvalidoException.cpfInvalido();
        }
        this.value = value;
    }
    public static CPF of(@NonNull String value) {
        return new CPF(value);
    }

    @Override
    public String toString() {
        return getValue();
    }
}
