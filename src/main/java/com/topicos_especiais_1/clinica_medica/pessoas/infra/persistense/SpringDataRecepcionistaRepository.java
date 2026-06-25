package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Recepcionista;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataRecepcionistaRepository extends JpaRepository<Recepcionista, UUID> {
    Optional<Recepcionista> findByUsuarioCpf(Cpf usuarioCpf);

    @Query(value = """
        SELECT distinct r FROM Recepcionista r 
        JOIN FETCH r.usuario
        WHERE ((CAST(:cursor AS uuid ) IS NULL OR r.id > :cursor ))
        AND(
           CAST(:busca AS string ) IS NULL
           OR LOWER(r.usuario.nome.value) LIKE LOWER(CONCAT('%',CAST(:busca AS string ), '%')) 
           OR LOWER(r.usuario.cpf.value) LIKE LOWER(CONCAT('%',CAST(:busca AS string ), '%')) 
           OR LOWER(r.usuario.email.value) LIKE LOWER(CONCAT('%',CAST(:busca AS string ), '%')) 
        )
        ORDER BY r.id ASC 
""")
    List<Recepcionista> buscaPaginada(
            @Param("cursor") UUID cursor,
            @Param("busca") String busca,
            Pageable pageable
    );
    @Query(value = """
        SELECT r FROM Recepcionista r
        JOIN FETCH r.usuario
        WHERE r.id = :recepcionistaId
""")
    Optional<Recepcionista> buscarPorIdComDetalhes(UUID recepcionistaId);

}
