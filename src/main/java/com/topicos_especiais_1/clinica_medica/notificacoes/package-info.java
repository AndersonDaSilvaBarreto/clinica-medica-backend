@ApplicationModule(
        displayName = "Notification Module",
        allowedDependencies = {"identidade :: events", "identidade :: Identity-security", "consulta :: events", "shared", "identidade :: Identity-Dados", "identidade :: Identity-entity-folder", "identidade"}
)
package com.topicos_especiais_1.clinica_medica.notificacoes;

import org.springframework.modulith.ApplicationModule;