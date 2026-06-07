package com.topicos_especiais_1.clinica_medica;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ApplicationModularityTest {
    @Test
    void verifyModularStructure() {
        ApplicationModules modules = ApplicationModules.of(ClinicaMedicaApplication.class);
        modules.verify();
    }
}
