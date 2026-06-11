package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Paciente;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.PacienteRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.CPF;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
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
    @Cacheable(value = "pacientePorId", key = "#id")
    public Paciente buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> EntidadeNaoEncontradaException.porId(
                EntidadeNaoEncontradaException.PACIENTE,
                id
        ));
    }

    @Override
    @Cacheable(value = "pacientePorCpf",key = "#cpf")
    public Paciente buscarPorCPF(CPF cpf) {
        return repository.findByCpf(cpf).orElseThrow(() ->
                EntidadeNaoEncontradaException.porCampo(
                        EntidadeNaoEncontradaException.PACIENTE,
                        "cpf",
                        cpf.toString())

        );
    }

    @Override
    public void deletarPorId(UUID id) {
        repository.deleteById(id);
    }
}
