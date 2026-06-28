package com.topicos_especiais_1.clinica_medica.shared.domain.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import com.github.f4b6a3.uuid.UuidCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@MappedSuperclass
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity implements Serializable {
    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id = UuidCreator.getTimeOrderedEpoch();
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private Instant dataCriacao = Instant.now();


    public UUID getId() {
        return this.id;
    }
}
