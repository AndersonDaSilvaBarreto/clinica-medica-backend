package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.api.UsuarioApi;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.PacienteRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.PacienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarPacientePorIdUseCase {
    private final UsuarioApi usuarioApi;
    private final PacienteRepository pacienteRepository;

    @Transactional(readOnly = true)
    public PacienteResponse execute(UUID pacienteId) {
        var paciente = pacienteRepository.buscarPorId(pacienteId);
        var usuario = usuarioApi.buscarUsuarioPorId(paciente.getUsuario().getId());
        return PacienteResponse.ofPacienteAndUsuario(paciente,usuario);
    }
}
