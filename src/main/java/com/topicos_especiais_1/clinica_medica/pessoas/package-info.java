@ApplicationModule(
        displayName = "pessoas module",
        allowedDependencies = {"shared", "identidade :: events"}
)
package com.topicos_especiais_1.clinica_medica.pessoas;

import org.springframework.modulith.ApplicationModule;