package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Especialidade;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataEspecialidadeRepository extends JpaRepository<Especialidade, UUID> {
    boolean existsByNome(Nome nome);
    @Query(value = """
        SELECT e FROM Especialidade e
        WHERE (:cursor IS NULL OR e.id > :cursor)
            AND (:busca IS NULL OR LOWER(e.nome) LIKE :busca)
        ORDER BY e.id ASC
""")
    List<Especialidade> buscaPaginada(
            @Param("cursor") UUID cursor,
            @Param("busca") String busca,
            Pageable pageable
    );

    @Query("""
        SELECT e FROM Medico m
        JOIN m.especialidades e
        WHERE m.id = :medicoId AND e.id = :especialidadeId
""")
    Optional<Especialidade> findEspecialidadeByMedicoIdEspecialidadeId(
            @Param("especialidadeId") UUID especialidadeId,
            @Param("medicoId") UUID medicoId);
}
