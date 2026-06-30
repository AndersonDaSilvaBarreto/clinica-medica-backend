@ApplicationModule(displayName = "Dashboard-Module",allowedDependencies = {"consulta :: Consulta-Repositories", "pessoas :: Pessoas-Domain-Repository", "consulta :: Consulta-Package-Entity", "consulta", "consulta :: Consulta-Infra", "pessoas :: Pessoas-Entities", "pessoas :: Pessoas-Infra-Persistense", "consulta :: Consulta-Enums", "shared"})
package com.topicos_especiais_1.clinica_medica.dashboard;

import org.springframework.modulith.ApplicationModule;