package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.api.UsuarioApi;
import com.topicos_especiais_1.clinica_medica.identidade.api.dto.UsuarioResumo;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.PacienteRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.PacienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarPacientePorUsuarioIdUseCase {
    private final PacienteRepository repository;
    private final UsuarioApi usuarioApi;

    @Transactional(readOnly = true)
    public PacienteResponse execute(UUID usuarioId) {
        UsuarioResumo usuarioResumo = usuarioApi.buscarPorId(usuarioId);
        Paciente paciente = repository.buscarPorUsuarioId(usuarioResumo.id());

        return PacienteResponse.ofPacienteAndUsuario(paciente,usuarioResumo);


    }
}
