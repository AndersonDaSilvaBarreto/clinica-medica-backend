package com.topicos_especiais_1.clinica_medica.pessoas.domain.entity;



import com.github.f4b6a3.uuid.UuidCreator;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Descricao;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Valor;
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
@Table(name = "especialidades")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Especialidade implements Serializable {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "nome", unique = true, nullable = false)
    )
    private Nome nome;

    @Embedded
    private Descricao descricao;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "valor_consulta"))
    private Valor valorConsulta;

    private Especialidade(
            Nome nome,
            Descricao descricao,
            Valor valorConsulta
    ) {
        this.id = UuidCreator.getTimeOrderedEpoch();
        this.nome = nome;
        this.descricao = descricao;
        this.valorConsulta = valorConsulta;
    }
    public static Especialidade create(
            Nome nome,
            Descricao descricao,
            Valor valorConsulta
    ) {
        return new Especialidade(
                Objects.requireNonNull(nome),
                descricao,
                Objects.requireNonNull(valorConsulta)
        );
    }
    public Optional<Descricao> getDescricao() {
        return Optional.ofNullable(this.descricao);
    }
    public void mudarNome(Nome nome) {
        this.nome = Objects.requireNonNull(nome);
    }
    public void mudarDescricao(Descricao descricao) {
        this.descricao = Objects.requireNonNull(descricao);
    }
    public void mudarValorConsulta(Valor valorConsulta) {
        this.valorConsulta = Objects.requireNonNull(valorConsulta);
    }
}
