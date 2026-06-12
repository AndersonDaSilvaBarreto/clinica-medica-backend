package com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.DataNascimentoInvalidaException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DataNascimento implements Serializable {
    @Column(name = "data_nascimento")
    @EqualsAndHashCode.Include
    private LocalDate value;

    private DataNascimento(@NonNull LocalDate value) {
        if(value.isAfter(LocalDate.now())) {
            throw DataNascimentoInvalidaException.dataInvalida();
        }
        this.value = value;
    }

    public static DataNascimento of(@NonNull LocalDate value) {
        return new DataNascimento(value);
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}
