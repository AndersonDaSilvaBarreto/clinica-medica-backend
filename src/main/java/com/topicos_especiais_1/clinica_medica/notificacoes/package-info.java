@ApplicationModule(
        displayName = "Notification Module",
        allowedDependencies = {"identidade :: events", "shared", "identidade :: Identity-entity-folder", "identidade :: Identity-security"}
)
package com.topicos_especiais_1.clinica_medica.notificacoes;

import org.springframework.modulith.ApplicationModule;