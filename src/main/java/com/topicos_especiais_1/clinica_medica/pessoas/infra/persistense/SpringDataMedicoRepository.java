package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.Crm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataMedicoRepository extends JpaRepository<Medico, UUID> {
    Optional<Medico> findByCrm(Crm crm);
    boolean existsByCrm(Crm crm);

    @Query(
            """
            SELECT DISTINCT m
            FROM Medico m
            JOIN FETCH m.especialidades e 
            JOIN FETCH m.usuario u
            WHERE 
                (cast(:cursor AS uuid ) IS NULL OR m.id > :cursor)
                AND (
                CAST(:busca AS string ) IS NULL 
                OR LOWER(u.nome.value) LIKE LOWER(CONCAT('%', CAST(:busca AS string ), '%'))
                OR LOWER(m.crm.value) LIKE LOWER(CONCAT('%', CAST(:busca AS string ), '%'))
                OR LOWER(e.nome.value) LIKE LOWER(CONCAT('%', CAST(:busca AS string ), '%'))
                )
            ORDER BY m.id ASC 
"""
    )
    List<Medico> buscaPaginada(
            @Param("cursor") UUID cursor,
            @Param("busca") String busca,
            Pageable pageable
    );
    @Query(value = """
        SELECT m FROM Medico m
        JOIN FETCH m.usuario
        JOIN FETCH m.especialidades
        WHERE m.id = :medicoId
""")
    Optional<Medico> buscarPorIdComEspecialidades(@Param("medicoId") UUID medicoId);
}
