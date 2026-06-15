package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.identidade.domain.exception.UsuarioNaoEncontradoException;
import com.topicos_especiais_1.clinica_medica.identidade.domain.repository.UsuarioRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.*;
import com.topicos_especiais_1.clinica_medica.identidade.web.dto.UsuarioResponse;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarUsuarioPorIDUseCaseTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private BuscarUsuarioPorIDUseCase useCase;

    @Test void deveRetornarUsuarioQuandoIdExistir () {

        UUID id = UUID.randomUUID();

        Usuario usuario = Usuario.createPaciente(
                Nome.of("Teste"),
                Email.of("teste@gmail.com"),
                Senha.ofHash("kfakf;a"),
                Genero.MASCULINO,
                Cpf.of("86429118088"),
                DataNascimento.of(LocalDate.parse("2002-05-20")),
                Telefone.of("55222227777")
        );
        when(repository.buscarPorId(id))
                .thenReturn(usuario);

        UsuarioResponse response = useCase.execute(id);

        assertEquals(
                "Teste",
                response.nome()
        );
        assertEquals(
                "teste@gmail.com",
                response.email()
        );

        verify(repository)
                .buscarPorId(id);



    }

    @Test
    void deveLancarExcepcaoQuandoUsuarioNaoExistir() {
        UUID id = UUID.randomUUID();

        when(repository.buscarPorId(id))
                .thenThrow(UsuarioNaoEncontradoException.porId(id));

        assertThrows(UsuarioNaoEncontradoException.class,
                () -> useCase.execute(id));
        verify(repository)
                .buscarPorId(id);
    }

}