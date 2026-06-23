package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.api.UsuarioApi;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Recepcionista;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.RecepcionistaRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.CriarRecepcionistaRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeExistenteException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarRecepcionistaUseCase {
    private final RecepcionistaRepository repository;
    private final UsuarioApi usuarioApi;
    @Transactional
    public void execute(CriarRecepcionistaRequest request) {
        boolean usuarioExiste = usuarioApi.existePorCpf(Cpf.of(request.cpf()));
        if(usuarioExiste) {
            throw EntidadeExistenteException.porCampo(
                    EntidadeExistenteException.RECEPCIONISTA,
                    "Cpf",
                    request.cpf()
            );
        }
        var usuario = usuarioApi.criarFuncionario(
                request.nome(),
                Email.of(request.email()),
                request.genero(),
                Cpf.of(request.cpf()),
                Perfil.RECEPCIONISTA,
                request.dataNascimento(),
                request.telefone()
        );

        var recepcionista = Recepcionista.create(usuario);
        repository.salvar(recepcionista);
    }
}
