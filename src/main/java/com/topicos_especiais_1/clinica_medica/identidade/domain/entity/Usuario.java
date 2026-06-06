package com.topicos_especiais_1.clinica_medica.identidade.domain.entity;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Perfil;
import com.topicos_especiais_1.clinica_medica.shared.domain.Email;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Senha;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.UsuarioId;
import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "usuarios")
@Entity
@Getter

public class Usuario {
    @EmbeddedId
    private UsuarioId id;

    @Embedded
    private Senha senha;

    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private Perfil perfil;



}
