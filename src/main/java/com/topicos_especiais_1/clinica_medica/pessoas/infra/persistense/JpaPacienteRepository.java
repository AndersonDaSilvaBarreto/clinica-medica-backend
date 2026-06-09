package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.service.PacienteRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.CPF;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaPacienteRepository implements PacienteRepository {
    private final SpringDataPacienteRepository repository;
    @Override
    public Paciente salvar(Paciente paciente) {
        return repository.save(paciente);
    }

    @Override
    public Paciente atualizar(Paciente paciente) {
        return repository.save(paciente);
    }

    @Override
    public Optional<Paciente> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Paciente> buscarPorCPF(CPF cpf) {
        return repository.findByCpf(cpf);
    }

    @Override
    public void deletarPorId(UUID id) {
        repository.deleteById(id);
    }
}
