package com.topicos_especiais_1.clinica_medica.agenda.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Descricao;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "salas_atendimento")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SalaAtendimento implements Serializable {
    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id;

    @Embedded
    private Nome nome;

    @Embedded
    private Descricao descricao;

    @Column(name = "ativa", nullable = false)
    private Boolean ativa;

     private SalaAtendimento(Nome nome, Descricao descricao, Boolean ativa) {
        this.id = UuidCreator.getTimeOrderedEpoch();
        this.nome = Objects.requireNonNull(nome);
        this.descricao = descricao;
        this.ativa = ativa != null ? ativa : true;
    }
    public static SalaAtendimento create(Nome nome, Descricao descricao, Boolean ativa) {
        return new SalaAtendimento(nome,descricao,ativa);
    }
    public void mudarNome(Nome nome) {
         this.nome = Objects.requireNonNull(nome);
    }
    public void mudarDescricao(Descricao descricao) {
         this.descricao = Objects.requireNonNull(descricao);
    }
    public void mudarAtivo(Boolean ativa) {
         this.ativa = Objects.requireNonNull(ativa);
    }
    public Optional<Descricao> getDescricao() {
         return Optional.ofNullable(descricao);
    }

}
