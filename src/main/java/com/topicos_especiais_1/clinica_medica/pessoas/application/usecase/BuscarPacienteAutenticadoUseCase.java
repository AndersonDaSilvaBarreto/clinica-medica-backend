package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.PacienteRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.PacienteResponse;
import com.topicos_especiais_1.clinica_medica.shared.infra.security.UsuarioAutenticado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BuscarPacienteAutenticadoUseCase {
    private final PacienteRepository repository;
    @Transactional(readOnly = true)
    public PacienteResponse execute(UsuarioAutenticado usuarioAutenticado) {
        Paciente paciente = repository.buscarPorUsuarioId(usuarioAutenticado.getId());

        return PacienteResponse.ofPacienteAndUsuarioAutenticado(paciente,usuarioAutenticado);


    }
}
