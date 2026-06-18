@ApplicationModule(
        displayName = "pessoas module",
        allowedDependencies = {"shared", "identidade :: events", "identidade :: usuario-api", "identidade :: Identity-entity-folder", "identidade :: usuario-api-dto", "identidade :: Indentity-value-objects"}
)
package com.topicos_especiais_1.clinica_medica.pessoas;

import org.springframework.modulith.ApplicationModule;