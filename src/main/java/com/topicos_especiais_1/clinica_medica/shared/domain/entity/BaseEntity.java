package com.topicos_especiais_1.clinica_medica.shared.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity {
    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id = UuidCreator.getTimeOrderedEpoch();
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private Instant dataCriacao = Instant.now();
}
