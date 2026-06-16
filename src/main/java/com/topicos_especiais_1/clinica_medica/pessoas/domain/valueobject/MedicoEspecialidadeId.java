package com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MedicoEspecialidadeId implements Serializable {
    @EqualsAndHashCode.Include
    @Column(name = "medico_id")
    private UUID medicoId;
    @EqualsAndHashCode.Include
    @Column(name = "especialidade_id")
    private UUID especialidadeId;

    private MedicoEspecialidadeId(UUID medicoId, UUID especialidadeId) {
        this.medicoId = Objects.requireNonNull(medicoId);
        this.especialidadeId = Objects.requireNonNull(especialidadeId);
    }
    public static MedicoEspecialidadeId of(UUID medicoId, UUID especialidadeId) {
        return new MedicoEspecialidadeId(medicoId, especialidadeId);
    }

}
