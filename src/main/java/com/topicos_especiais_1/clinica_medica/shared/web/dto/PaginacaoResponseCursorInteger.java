package com.topicos_especiais_1.clinica_medica.shared.web.dto;

import java.util.List;

public record PaginacaoResponseCursorInteger<T>(
        List<T> content,
        Integer nextCursor,
        boolean hasNext
) {
}
