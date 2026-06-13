package com.topicos_especiais_1.clinica_medica.shared.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.shared.domain.exception.CPFInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CpfTest {

    @Test
    void testCpfInvalid() {
        assertThrows(CPFInvalidoException.class,
                () -> Cpf.of(""));
    }
    @Test
    void testCpfRetornarCorretamente() {
       Cpf cpf = Cpf.of("81605629006");

       assertEquals(
               "81605629006",
               cpf.toString()
       );
    }



}