package com.topicos_especiais_1.clinica_medica.pessoas.api;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Recepcionista;

import java.util.UUID;

public interface RecepcionistaApi {
    Recepcionista buscarPorUsuarioId(UUID usuarioId);
}
