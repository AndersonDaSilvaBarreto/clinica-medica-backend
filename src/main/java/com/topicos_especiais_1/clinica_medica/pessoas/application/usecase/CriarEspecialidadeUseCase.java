package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Especialidade;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.EspecialidadeRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.CriarEspecialidadeRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeExistenteException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Descricao;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Valor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarEspecialidadeUseCase {
    private final EspecialidadeRepository repository;

    @Transactional
    public void execute(CriarEspecialidadeRequest request) {
        if(repository.existePorNome(Nome.of(request.nome()))) {
            throw EntidadeExistenteException.porCampo(
                    EntidadeExistenteException.ESPECIALIDADE,
                    "nome",
                    request.nome()
            );
        }
        var especialidade = Especialidade.create(
                Nome.of(request.nome()),
                request.descricao() != null ? Descricao.of(request.descricao()) : null,
                Valor.of(request.valorConsulta())
        );
        repository.salvar(especialidade);
    }
}
