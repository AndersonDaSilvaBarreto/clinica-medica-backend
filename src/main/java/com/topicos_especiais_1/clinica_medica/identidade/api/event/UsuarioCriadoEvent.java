package com.topicos_especiais_1.clinica_medica.identidade.api.event;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.CPF;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;

import java.util.UUID;

public record UsuarioCriadoEvent(
        UUID usuarioId,
        Email email,
        Perfil perfil,
        CPF cpf
) {
}
