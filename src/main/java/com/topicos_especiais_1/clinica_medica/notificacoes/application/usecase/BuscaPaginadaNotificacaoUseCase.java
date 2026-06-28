package com.topicos_especiais_1.clinica_medica.notificacoes.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.entity.Notificacao;
import com.topicos_especiais_1.clinica_medica.notificacoes.domain.repository.NotificacaoRepository;
import com.topicos_especiais_1.clinica_medica.notificacoes.web.dto.NotificacaoResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscaPaginadaNotificacaoUseCase {
    private final NotificacaoRepository notificacaoRepository;

    @Transactional(readOnly = true)
    public PaginacaoResponse<NotificacaoResponse> execute(UUID cursor, Usuario usuarioAutenticado, Boolean lida, int limit) {
        List<Notificacao> notificacoes = notificacaoRepository.buscaPaginada(cursor,usuarioAutenticado.getId(),lida,limit + 1);
        boolean hasNext = notificacoes.size() > limit;
        List<NotificacaoResponse> response = notificacoes.stream()
                .limit(limit)
                .map(NotificacaoResponse::fromEntity)
                .toList();
        return new PaginacaoResponse<>(
                response,
                hasNext ? response.getLast().id() : null,
                hasNext
        );
    }
}
