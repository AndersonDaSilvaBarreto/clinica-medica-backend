package com.topicos_especiais_1.clinica_medica.atendimento.application.usecase;

import com.topicos_especiais_1.clinica_medica.agenda.domain.entity.SalaAtendimento;
import com.topicos_especiais_1.clinica_medica.agenda.domain.repository.SalaAtendimentoRepository;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.FilaAtendimento;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.repository.FilaAtendimentoRepository;
import com.topicos_especiais_1.clinica_medica.atendimento.infra.persistense.FilaAtendimentoSpecifications;
import com.topicos_especiais_1.clinica_medica.atendimento.web.dto.AdicionarFilaRequest;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ConsultaRepository;
import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.AcessoNegadoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.exception.ConflitoException;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Perfil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdicionarNaFilaUseCase {
    private final FilaAtendimentoRepository filaAtendimentoRepository;
    private final ConsultaRepository consultaRepository;
    private final SalaAtendimentoRepository salaAtendimentoRepository;

    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Sao_Paulo");
    @Transactional
    public void execute(AdicionarFilaRequest request, Usuario usuario) {
        Consulta consulta = consultaRepository.buscarPorId(request.consultaId());
        SalaAtendimento salaAtendimento = salaAtendimentoRepository.buscarPorId(request.salaId());

        UUID medicoId = consulta.getMedico().getId();
        LocalDate hoje = LocalDate.now(FUSO_HORARIO);

        if(!Perfil.RECEPCIONISTA.equals(usuario.getPerfil()) && !consulta.getMedico().getUsuario().equals(usuario)) {
            throw new AcessoNegadoException("Você não tem permissão de adicionar consulta na fila");
        }

        boolean jaExiste = filaAtendimentoRepository.existeConsultaNaFilaDoDia(request.consultaId(), hoje);
        if (jaExiste) {
            throw ConflitoException.of("Fila Atendimento", "Esta consulta já foi inserida na fila de atendimento hoje.");
        }

        Specification<FilaAtendimento> specsFila = Specification
                .where(FilaAtendimentoSpecifications.porMedicoId(medicoId))
                .and(FilaAtendimentoSpecifications.porDataDia(hoje));
        Pageable pageable = PageRequest.of(0,1, Sort.by(Sort.Direction.DESC,"ordemFila"));
        List<FilaAtendimento> resultadoFila = filaAtendimentoRepository.buscaPaginada(specsFila,pageable);
        int proximaOrdem = resultadoFila.isEmpty() ? 1 : resultadoFila.getFirst().getOrdemFila() + 1;

        FilaAtendimento novaFila = FilaAtendimento.create(
                consulta,
                consulta.getMedico(),
                consulta.getPaciente(),
                salaAtendimento,
                proximaOrdem,
                hoje
        );
        filaAtendimentoRepository.salvar(novaFila);
    }
}
