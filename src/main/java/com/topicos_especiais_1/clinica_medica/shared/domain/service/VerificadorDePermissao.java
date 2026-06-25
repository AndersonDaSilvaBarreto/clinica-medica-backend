package com.topicos_especiais_1.clinica_medica.shared.domain.service;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;

public class VerificadorDePermissao {
    public static void EhAdministradorOuProprioUsuario(UsuarioAutenticado usuarioAutenticado, Usuario usuario) {
        boolean ehAdministrador =
                usuarioAutenticado.usuario().getPerfil() == Perfil.ADMINISTRADOR;

        boolean ehProprioMedico =  usuarioAutenticado.usuario().equals(usuario);
        if (!ehAdministrador && !ehProprioMedico) {
            throw new AcessoNegadoException();
        }
    }
}
