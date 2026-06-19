package com.topicos_especiais_1.clinica_medica.pessoas.domain.repository;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.PacienteResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PacienteRepository {
    Paciente salvar(Paciente paciente);
    Paciente atualizar(Paciente paciente);
    Paciente buscarPorId(UUID id);
    Paciente buscarPorUsuarioId(UUID usuarioId);
    void deletar(Paciente paciente);
    List<PacienteResponse> buscarPacientes(
            UUID cursor,
            int limit,
            String busca
    );
    List<Paciente> buscaPagientesPaginado(UUID cursor, int limit, String busca);
}
