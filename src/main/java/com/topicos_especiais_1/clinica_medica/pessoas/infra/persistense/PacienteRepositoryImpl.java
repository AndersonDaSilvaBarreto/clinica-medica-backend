package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.identidade.api.UsuarioApi;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.PacienteRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.PacienteResponse;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PacienteRepositoryImpl implements PacienteRepository {
    private static final String CACHE_POR_ID = "pacientePorId";
    private static final String CACHE_POR_USUARIO_ID = "pacientePorUsuarioId";
    private final SpringDataPacienteRepository repository;
    private final JdbcClient jdbcClient;
    private final UsuarioApi usuarioApi;

    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID,key = "#paciente.id"),
            @CacheEvict(value = CACHE_POR_USUARIO_ID, key = "#paciente.usuarioId")
    })
    public Paciente salvar(Paciente paciente) {
        var pacienteSaved = repository.save(paciente);
        usuarioApi.apagarCache(pacienteSaved.getUsuario());
        return pacienteSaved;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID,key = "#paciente.id"),
            @CacheEvict(value = CACHE_POR_USUARIO_ID, key = "#paciente.usuarioId")
    })
    public Paciente atualizar(Paciente paciente) {
        return repository.save(paciente);
    }

    @Override
    @Cacheable(value = CACHE_POR_ID, key = "#id")
    public Paciente buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> EntidadeNaoEncontradaException.porId(
                EntidadeNaoEncontradaException.PACIENTE,
                id
        ));
    }

    @Override
    @Cacheable(value = CACHE_POR_USUARIO_ID,  key = "#usuarioId")
    public Paciente buscarPorUsuarioId(UUID usuarioId) {
        return repository.findByUsuarioId(usuarioId).orElseThrow(() -> EntidadeNaoEncontradaException.porId(
                EntidadeNaoEncontradaException.USUARIO,
                usuarioId
        ));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CACHE_POR_ID,key = "#paciente.id"),
            @CacheEvict(value = CACHE_POR_USUARIO_ID, key = "#paciente.usuarioId")
    })
    public void deletar(Paciente paciente) {
        repository.delete(paciente);
    }

    @Override
    public List<PacienteResponse> buscarPacientes(UUID cursor, int limit, String busca) {
        return jdbcClient.sql(
               """
        SELECT p.id,
               u.nome,
               u.email,
               u.telefone,
               u.cpf,
               u.data_nascimento,
               p.endereco
        FROM pacientes p
        INNER JOIN usuarios u
            ON u.id = p.usuario_id
        WHERE
            (:cursor::uuid IS NULL OR p.id > :cursor)
            AND  (
                :busca::text IS NULL
                OR LOWER(u.nome) LIKE LOWER(CONCAT('%',:busca::text, '%'))
                OR LOWER(u.email)  LIKE LOWER(CONCAT('%', :busca::text, '%'))
                OR u.cpf LIKE CONCAT('%', :busca::text, '%')
            )
        ORDER BY p.id
        LIMIT :limit
        """
       )
               .param("cursor", cursor)
               .param("busca", busca)
               .param("limit", limit)
               .query((rs, _) -> new PacienteResponse(
                       rs.getObject("id", UUID.class),
                       rs.getString("nome"),
                       rs.getString("email"),
                       rs.getString("telefone"),
                       rs.getString("cpf"),
                       rs.getObject("data_nascimento", LocalDate.class),
                       rs.getString("endereco")

               )).list();
    }

    @Override
    @Query(value = """

""")
    public List<Paciente> buscaPagientesPaginado(UUID cursor, int limit, String busca) {
        return List.of();
    }
}
