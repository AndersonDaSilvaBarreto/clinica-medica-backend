package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Convenio;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.ConvenioRepository;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaConvenioRepository implements ConvenioRepository {
    private final SpringDataConvenioRepository repository;
    @Override
    public Convenio salvar(Convenio convenio) {
        return repository.save(convenio);
    }

    @Override
    public Convenio atualizar(Convenio convenio) {
        return repository.save(convenio);
    }

    @Override
    public Convenio buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(
                () -> EntidadeNaoEncontradaException.porId(
                        EntidadeNaoEncontradaException.CONVENIO,
                        id
                )
        );
    }

    @Override
    public boolean existePorid(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public void deletarPorId(UUID id) {
        repository.deleteById(id);
    }
}
