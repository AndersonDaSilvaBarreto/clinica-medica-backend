package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.EspecialidadeRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtualizarEspecialidadeRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Descricao;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Valor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtualizarEspecialidadeUseCase {
    private final EspecialidadeRepository repository;
    @Transactional()
    public void execute(UUID especialidadeId, AtualizarEspecialidadeRequest request) {
        var especialidade = repository.buscarPorId(especialidadeId);
        if(request.nome() != null) especialidade.mudarNome(Nome.of(request.nome()));
        if(request.descricao() != null) especialidade.mudarDescricao(Descricao.of(request.descricao()));
        if(request.valorConsulta() != null) especialidade.mudarValorConsulta(Valor.of(request.valorConsulta()));
        repository.atualizar(especialidade);
    }
}
