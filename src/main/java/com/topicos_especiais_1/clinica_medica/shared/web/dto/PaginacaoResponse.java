package com.topicos_especiais_1.clinica_medica.shared.web.dto;


import java.util.List;
import java.util.UUID;

public record PaginacaoResponse<T>(
    List<T> content,
   UUID nextCursor,
    boolean hasNext
) {

}
