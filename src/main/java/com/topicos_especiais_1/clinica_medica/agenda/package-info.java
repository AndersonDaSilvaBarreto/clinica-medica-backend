@ApplicationModule(
        displayName = "Agenda Module",
        allowedDependencies = {"pessoas :: Pessoas-Entities", "shared", "pessoas :: pessoas-api"}
)
package com.topicos_especiais_1.clinica_medica.agenda;

import org.springframework.modulith.ApplicationModule;