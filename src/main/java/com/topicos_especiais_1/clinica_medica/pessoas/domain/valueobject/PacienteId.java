package com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.EntityId;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PacienteId extends EntityId {
    private PacienteId(UUID value) {
        super(value);
    }
    public static PacienteId generate() {
        return new PacienteId(EntityId.generateUuidV7());
    }
    public static PacienteId of(@NonNull UUID value) {
        return new PacienteId(value);
    }
}
