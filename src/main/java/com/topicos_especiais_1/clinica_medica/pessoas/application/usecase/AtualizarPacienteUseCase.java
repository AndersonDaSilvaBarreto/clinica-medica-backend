package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.api.UsuarioApi;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.PacienteRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtualizarDadosPacienteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtualizarPacienteUseCase {
    private final UsuarioApi usuarioApi;
    private final PacienteRepository repository;
    public void execute(UUID usuarioId, AtualizarDadosPacienteRequest request) {
        var paciente = repository.buscarPorUsuarioId(usuarioId);
        if(request.telefone() != null) usuarioApi.trocarTelefone(usuarioId,request.telefone());
        if(request.dataNascimento() != null) usuarioApi.trocarDataNascimento(usuarioId, request.dataNascimento());
        if(request.endereco() != null) {
            paciente.mudarEndereco(request.endereco());
            repository.atualizar(paciente);
        }


    }
}
