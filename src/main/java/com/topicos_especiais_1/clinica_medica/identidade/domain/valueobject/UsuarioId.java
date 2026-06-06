package com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject;

import com.github.f4b6a3.uuid.UuidCreator;
import com.topicos_especiais_1.clinica_medica.shared.domain.EntityId;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class UsuarioId extends EntityId {
    private UsuarioId(UUID value){
        super(value);
    }
    public static UsuarioId generate() {
        return new UsuarioId(UuidCreator.getTimeOrderedEpoch());
    }
    public static UsuarioId of(@NonNull UUID value) {
        return new UsuarioId(value);
    }


}
