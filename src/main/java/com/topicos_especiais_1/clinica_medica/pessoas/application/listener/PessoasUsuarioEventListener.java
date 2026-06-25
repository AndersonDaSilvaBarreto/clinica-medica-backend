package com.topicos_especiais_1.clinica_medica.pessoas.application.listener;

import com.topicos_especiais_1.clinica_medica.identidade.api.UsuarioApi;
import com.topicos_especiais_1.clinica_medica.identidade.api.event.UsuarioCriadoEvent;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PessoasUsuarioEventListener {
    private final PacienteRepository repository;
    private final UsuarioApi usuarioApi;
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onUsuarioCriadoEvent(UsuarioCriadoEvent event) {
        Paciente novoPaciente = Paciente.create(
                usuarioApi.buscarUsuarioPorId(event.usuarioId()),
                event.endereco()
        );
        repository.salvar(novoPaciente);
    }
}
