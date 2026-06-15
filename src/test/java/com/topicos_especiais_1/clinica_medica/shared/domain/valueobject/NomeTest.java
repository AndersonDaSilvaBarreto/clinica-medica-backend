package com.topicos_especiais_1.clinica_medica.shared.domain.valueobject;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Nome;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.FormatoNomeInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NomeTest {

    @Test
     void
    testeDeveLancarExcecaoQuandoNomeInvalido() {

        assertThrows(FormatoNomeInvalidoException.class,
                () -> Nome.of("A"));

    }
    @Test
     void testeDeveCriarNomeCorretamente() {
        Nome nome = Nome.of("Anderson");
        assertEquals("Anderson", nome.toString());
    }

}