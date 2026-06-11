package com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.identidade.domain.service.SenhaValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Embeddable
@NoArgsConstructor
@Getter
public final class Senha implements Serializable {
    @Column(name = "senha", nullable = false)
    private String value;

    private Senha(@NonNull String value) {
        this.value = value;
    }
    public static Senha of(@NonNull String value) {
        SenhaValidator.validar(value);
        return new Senha(value);
    }
    public static Senha ofHash(@NonNull String hash) {
        return new Senha(hash);
    }

    @Override
    public String toString() {
        return getValue();
    }
}
