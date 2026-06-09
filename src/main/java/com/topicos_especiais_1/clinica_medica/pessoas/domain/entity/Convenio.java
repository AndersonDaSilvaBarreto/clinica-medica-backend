package com.topicos_especiais_1.clinica_medica.pessoas.domain.entity;

import com.github.f4b6a3.uuid.UuidCreator;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Descricao;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Telefone;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;
import java.util.UUID;

@Table(name = "convenios")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Convenio {
    @Id
    @EqualsAndHashCode.Include
    private UUID id = UuidCreator.getTimeOrderedEpoch();
    
    @Embedded
    private Nome nome;

    @Embedded
    private Telefone telefone;

    @Embedded
    private Descricao descricao;

    private Convenio(@NonNull Nome nome,  Telefone telefone, Descricao descricao) {
        this.nome = nome;
        this.telefone = telefone;
        this.descricao = descricao;
    }
    public static Convenio create(@NonNull Nome nome, @NonNull Telefone telefone, @NonNull Descricao descricao) {
        return new Convenio(nome,telefone,descricao);
    }

    public Optional<Telefone> getTelefone() {return Optional.ofNullable(telefone);}

}
