package com.topicos_especiais_1.clinica_medica.consulta.application.usecase;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.enums.StatusConsulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.consulta.web.dto.ConsultaResponse;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscaPaginadaMedicoMeConsultasUseCase {
    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;

    @Transactional(readOnly = true)
    public PaginacaoResponse<ConsultaResponse> execute(
            UUID cursor,
            UUID pacienteId,
            Usuario usuarioLogado,
            StatusConsulta status,
            Instant dataInicio,
            Instant dataFim,
            int limit
    ) {
        Medico medico = medicoRepository.buscarPorUsuarioId(usuarioLogado.getId());
        List<Consulta> consultas = consultaRepository.buscarPaginada(
                cursor,
                pacienteId,
                medico.getId(),
                status,
                dataInicio,
                dataFim,
                limit + 1
        );
        boolean hasNext = consultas.size() > limit;
        List<ConsultaResponse> response =  consultas.stream()
                .limit(limit)
                .map(ConsultaResponse::fromEntity)
                .toList();

        return new PaginacaoResponse<>(
                response,
                hasNext ? response.getLast().id(): null,
                hasNext
        );
    }
}
