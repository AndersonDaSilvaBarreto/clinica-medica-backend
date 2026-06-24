package com.topicos_especiais_1.clinica_medica.agenda.infra.persistense;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.SalaAtendimento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SpringDataSalaAtendimento extends JpaRepository<SalaAtendimento, UUID> {

    @Query(value = """
            SELECT s FROM SalaAtendimento s
               WHERE (:cursor IS NULL OR s.id > :cursor)
                 AND (:busca IS NULL OR LOWER(s.nome) LIKE :busca)
                 AND (:ativa IS NULL OR s.ativa = :ativa)
               ORDER BY s.id ASC
""")
    List<SalaAtendimento> buscaPaginada(
            @Param("cursor") UUID cursor,
            @Param("busca") String busca,
            @Param("ativa") Boolean ativa,
            Pageable pageable
    );
}
