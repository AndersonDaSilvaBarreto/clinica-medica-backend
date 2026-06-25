package com.topicos_especiais_1.clinica_medica.agenda.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.SalaAtendimentoRepository;
import com.topicos_especiais_1.clinica_medica.agenda.web.dto.AtualizarSalaAtendimentoRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Descricao;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtualizarSalaAtendimentoUseCase {
    private final SalaAtendimentoRepository repository;

    @Transactional
    public void execute(UUID salaId, AtualizarSalaAtendimentoRequest request) {
        var sala = repository.buscarPorId(salaId);
        if(request.nome() != null && !request.nome().isBlank()) sala.mudarNome(Nome.of(request.nome()));
        if(request.descricao() != null && !request.descricao().isBlank()) {
            sala.mudarDescricao(Descricao.of(request.descricao()));
        }
        if(request.ativa() != null) sala.mudarAtivo(request.ativa());
        repository.salvar(sala);
    }
}
