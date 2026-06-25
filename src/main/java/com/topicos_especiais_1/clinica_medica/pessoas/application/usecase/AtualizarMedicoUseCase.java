package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.domain.valueobject.Telefone;
import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtualizarMedicoRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.service.VerificadorDePermissao;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Nome;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtualizarMedicoUseCase {
    private final MedicoRepository medicoRepository;

    @Transactional
    public void execute(UUID medicoId, UsuarioAutenticado usuarioAutenticado, AtualizarMedicoRequest request) {
        var medico = medicoRepository.buscarPorIdComEspecialidades(medicoId);
        VerificadorDePermissao.EhAdministradorOuProprioUsuario(usuarioAutenticado, medico.getUsuario());
        if (request.nome() != null) medico.getUsuario().mudarNome(Nome.of(request.nome()));
        if (request.telefone() != null) medico.getUsuario().mudarTelefone(Telefone.of(request.telefone()));
        if (request.tempoConsultaMinutos() != null) medico.mudarTempoConsulta(request.tempoConsultaMinutos());
        medicoRepository.salvar(medico);
    }
}
