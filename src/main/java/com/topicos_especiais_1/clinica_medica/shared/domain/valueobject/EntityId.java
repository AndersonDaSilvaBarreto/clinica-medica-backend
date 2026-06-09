package com.topicos_especiais_1.clinica_medica.shared.domain.valueobject;

import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class EntityId {

    @EqualsAndHashCode.Include
    @Column(name = "id")
    private UUID value;

    protected EntityId(@NonNull UUID value) {
        this.value = value;
    }

    protected static UUID generateUuidV7() {
        return UuidCreator.getTimeOrderedEpoch();
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}
