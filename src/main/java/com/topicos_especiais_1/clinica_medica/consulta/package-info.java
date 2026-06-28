@ApplicationModule(displayName = "Consulta Module", allowedDependencies = {"shared", "identidade :: Identity-entity-folder", "pessoas :: Pessoas-Entities", "pessoas :: Pessoas-Domain-Repository", "agenda", "agenda :: Agenda-Values-Objects", "pessoas :: pessoas-api", "agenda :: Agenda-Domain-Repository", "identidade :: Identity-security", "notificacoes :: Notificacao-Api-Events"})
package com.topicos_especiais_1.clinica_medica.consulta;

import org.springframework.modulith.ApplicationModule;