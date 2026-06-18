package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.identidade.api.UsuarioApi;
import com.topicos_especiais_1.clinica_medica.identidade.api.dto.UsuarioResumo;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Especialidade;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.EspecialidadeRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.valueobject.Crm;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.CriarMedicoRequest;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.EntidadeExistenteException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Email;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CriarMedicoUseCase {
    private final MedicoRepository medicoRepository;
    private final UsuarioApi usuarioApi;
    private final EspecialidadeRepository especialidadeRepository;
    @Transactional
    public void execute(CriarMedicoRequest request) {
        Crm crm = Crm.of(request.crm());
        if(medicoRepository.existePorCrm(crm)) throw EntidadeExistenteException.porCampo(
                EntidadeExistenteException.MEDICO,
                "Crm",
                crm.toString()

        );
        Cpf cpf = Cpf.of(request.cpf());
        if(usuarioApi.existePorCpf(cpf)) throw EntidadeExistenteException.porCampo(
                EntidadeExistenteException.USUARIO,
                "Cpf",
                cpf.toString()
        );
        UsuarioResumo usuarioResumo = usuarioApi.criarFuncionario(
                request.nome(),
                Email.of(request.email()),
                request.genero(),
                cpf,
                Perfil.MEDICO,
                request.dataNascimento(),
                request.telefone()
        );
        Medico medico = Medico.create(
                usuarioResumo.id(),
                crm,
                request.tempoConsultaMinutos(),
                true
        );
        for(UUID especialidadeId : request.especialidades()) {
            Especialidade especialidade = especialidadeRepository.buscarPorId(especialidadeId);
            medico.adicionarEspecialidade(especialidade);
        }
        medicoRepository.salvar(medico);

    }
}
