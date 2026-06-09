package com.topicos_especiais_1.clinica_medica.pessoas.application.listener;

import com.topicos_especiais_1.clinica_medica.identidade.api.event.UsuarioCriadoEvent;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.service.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PessoasUsuarioEventListener {
    private final PacienteRepository repository;

    @ApplicationModuleListener
    public void onUsuarioCriadoEvent(UsuarioCriadoEvent event) {
        Paciente novoPaciente = Paciente.create(
                event.usuarioId(),
                event.cpf(),
                null,
                null,
                null
        );
        repository.salvar(novoPaciente);
    }
}
