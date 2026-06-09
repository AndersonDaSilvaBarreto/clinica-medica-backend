package com.topicos_especiais_1.clinica_medica.identidade.api;

import com.topicos_especiais_1.clinica_medica.identidade.api.dto.UsuarioResumo;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioApi {
    UsuarioResumo buscarPorId(UUID id);
    UsuarioResumo buscarPorEmail(Email email);
}
