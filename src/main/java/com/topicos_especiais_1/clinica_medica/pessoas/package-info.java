@ApplicationModule(
        displayName = "pessoas module",
        allowedDependencies = {"shared", "identidade :: events", "identidade :: usuario-api", "identidade :: usuario-api-dto"}
)
package com.topicos_especiais_1.clinica_medica.pessoas;

import org.springframework.modulith.ApplicationModule;