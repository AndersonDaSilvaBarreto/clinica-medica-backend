package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.DataNascimento;
import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Telefone;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.RecepcionistaRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtualizarRecepcionistaRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.service.VerificadorDePermissao;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtualizarRecepcionistaUseCase {
    private final RecepcionistaRepository repository;

    @Transactional
    public void execute(UUID recepcionistaId, UsuarioAutenticado usuarioAutenticado, AtualizarRecepcionistaRequest request) {
        var recepcionista = repository.buscarPorIdComDatalhes(recepcionistaId);
        VerificadorDePermissao.EhAdministradorOuProprioUsuario(usuarioAutenticado, recepcionista.getUsuario());
        if(request.nome() != null) recepcionista.getUsuario().mudarNome(Nome.of(request.nome()));
        if(request.telefone() != null) recepcionista.getUsuario().mudarTelefone(Telefone.of(request.telefone()));
        if(request.dataNascimento() != null) recepcionista.getUsuario().mudarDataNascimento(DataNascimento.of(request.dataNascimento()));
        repository.salvar(recepcionista);
        
    }
}
