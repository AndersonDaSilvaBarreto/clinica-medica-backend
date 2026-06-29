@ApplicationModule(
        displayName = "Atendimento Module",
        allowedDependencies = {"consulta", "consulta :: Consulta-Package-Entity", "pessoas :: Pessoas-Entities", "shared", "agenda :: agenda-entities", "consulta :: Consulta-Repositories", "agenda :: Agenda-Domain-Repository"}
)
package com.topicos_especiais_1.clinica_medica.atendimento;

import org.springframework.modulith.ApplicationModule;