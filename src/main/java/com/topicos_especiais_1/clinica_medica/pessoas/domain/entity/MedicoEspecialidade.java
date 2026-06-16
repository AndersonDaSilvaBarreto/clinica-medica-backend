package com.topicos_especiais_1.clinica_medica.pessoas.domain.entity;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.MedicoEspecialidadeId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "medico_especialidades")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MedicoEspecialidade implements Serializable {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private MedicoEspecialidadeId id;

    private MedicoEspecialidade(
            MedicoEspecialidadeId medicoEspecialidadeId
    ) {
        this.id = Objects.requireNonNull(medicoEspecialidadeId);
    }
    public static MedicoEspecialidade create(
            MedicoEspecialidadeId medicoEspecialidadeId
    ) {
        return new MedicoEspecialidade(medicoEspecialidadeId);
    }


}
