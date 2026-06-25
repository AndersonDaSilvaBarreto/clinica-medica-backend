package com.topicos_especiais_1.clinica_medica.agenda.infra.persistense;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.BloqueioAgenda;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SpringDataBloqueioAgenda extends JpaRepository<BloqueioAgenda, UUID> {
    @Query(value = """
            SELECT b FROM BloqueioAgenda b
            WHERE (:cursor IS NULL OR b.id > :cursor)
                AND(:medicoId IS NULL OR b.medico.id = :medicoId)
                AND()
""")
    List<BloqueioAgenda> buscaPaginada(
            @Param("cursor") UUID cursor,
            @Param("medicoId") UUID medicoId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            Pageable pageable);
}
