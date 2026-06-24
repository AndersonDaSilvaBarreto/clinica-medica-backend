package com.topicos_especiais_1.clinica_medica.agenda.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.SalaAtendimento;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.SalaAtendimentoRepository;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.CriarSalaAtendimentoRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Descricao;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarSalaAtendimentoUseCase {
    private final SalaAtendimentoRepository repository;
    @Transactional
    public void execute(CriarSalaAtendimentoRequest request)  {
    var salaAtendimento = SalaAtendimento.create(
            Nome.of(request.nome()),
            request.descricao() != null ? Descricao.of(request.descricao()) : null,
            request.ativa()
    );
    repository.salvar(salaAtendimento);
    }
}
