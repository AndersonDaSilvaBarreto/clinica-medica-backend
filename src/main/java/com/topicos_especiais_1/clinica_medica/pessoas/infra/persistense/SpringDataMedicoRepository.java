package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.Crm;

public interface SpringDataMedicoRepository extends JpaRepository<Medico, UUID>, JpaSpecificationExecutor<Medico> {
    Optional<Medico> findByCrm(Crm crm);

    @EntityGraph(attributePaths = "usuario")
    Optional<Medico> findByUsuarioId(UUID usuarioId);
    boolean existsByCrm(Crm crm);

    @Query(
            """
            SELECT DISTINCT m
            FROM Medico m
            LEFT JOIN FETCH m.especialidades e
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
        LEFT JOIN FETCH m.especialidades
        LEFT JOIN FETCH m.salaAtendimento
        WHERE m.id = :medicoId
""")
    Optional<Medico> buscarPorIdComEspecialidades(@Param("medicoId") UUID medicoId);

    @Query(value = """
                  SELECT DISTINCT m FROM Medico m
                  LEFT JOIN fetch m.horariosAtendimento
                  WHERE m.id = :medicoId 
""")
    Optional<Medico> buscarMedicoComHorariosAtendimento(@Param("medicoId") UUID medicoId);



    @Query("""
           SELECT DISTINCT m FROM Medico m
           LEFT JOIN FETCH m.horariosAtendimento
           JOIN FETCH m.usuario
           """)
    List<Medico> buscarTodosComAgenda();

    @Query("""
        SELECT DISTINCT m FROM Medico m
        JOIN FETCH m.usuario
        LEFT JOIN FETCH m.especialidades
""")
    Page<Medico> findAllComRelacionamentos(Specification<Medico> specs, Pageable pageable);
    
}
