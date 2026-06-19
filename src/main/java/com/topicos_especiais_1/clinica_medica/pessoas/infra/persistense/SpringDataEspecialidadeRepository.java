package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Especialidade;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SpringDataEspecialidadeRepository extends JpaRepository<Especialidade, UUID> {
    boolean existsByNome(Nome nome);
    @Query(value = """
        SELECT e FROM Especialidade e
        WHERE (CAST(:cursor AS uuid) IS NULL OR e.id > :cursor)
            AND (CAST(:busca AS string) IS NULL OR LOWER(e.nome) LIKE LOWER(CONCAT('%', CAST(:busca AS string), '%')))
        ORDER BY e.id ASC
""")
    List<Especialidade> buscaPaginada(
            @Param("cursor") UUID cursor,
            @Param("busca") String busca,
            Pageable pageable
    );
}
