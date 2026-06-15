package com.topicos_especiais_1.clinica_medica.identidade.api;

import com.topicos_especiais_1.clinica_medica.identidade.api.dto.UsuarioResumo;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Telefone;

import java.time.LocalDate;
import java.util.UUID;

public interface UsuarioApi {
    UsuarioResumo buscarPorId(UUID id);
    UsuarioResumo buscarPorEmail(Email email);
    UsuarioResumo trocarTelefone(UUID usuarioId, String telefone);
    UsuarioResumo trocarDataNascimento( UUID usuarioId,LocalDate dataNascimento);

}
