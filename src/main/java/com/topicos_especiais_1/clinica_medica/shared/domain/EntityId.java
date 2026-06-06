package com.topicos_especiais_1.clinica_medica.shared.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class EntityId {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private UUID value;

    protected EntityId(@NonNull UUID value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}
