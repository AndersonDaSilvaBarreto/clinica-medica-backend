package com.topicos_especiais_1.clinica_medica.pessoas.domain.service;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.CPF;

import java.util.Optional;
import java.util.UUID;

public interface PacienteRepository {
    Paciente salvar(Paciente paciente);
    Paciente atualizar(Paciente paciente);
    Optional<Paciente> buscarPorId(UUID id);
    Optional<Paciente> buscarPorCPF(CPF cpf);
    void deletarPorId(UUID id);

}
