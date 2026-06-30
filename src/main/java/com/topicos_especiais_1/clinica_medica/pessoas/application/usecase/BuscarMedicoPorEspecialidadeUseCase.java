package com.topicos_especiais_1.clinica_medica.pessoas.application.usecase;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.repository.MedicoRepository;
import com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense.MedicoSpecifications;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.MedicoResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarMedicoPorEspecialidadeUseCase {
    private final MedicoRepository medicoRepository;

    public PaginacaoResponse<MedicoResponse> execute(
            UUID especialidadeId,
            UUID cursor,
            String nome,
            int limit

    ) {
        Specification<Medico> specs = Specification.where(MedicoSpecifications.porEspecialidadeId(especialidadeId))
                .and(MedicoSpecifications.idMaiorQue(cursor))
                .and(MedicoSpecifications.porNomeMedico(nome));
        Pageable pageable = PageRequest.of(
                0, limit + 1, Sort.by(Sort.Direction.ASC,"id"));
        List<Medico> medicos = medicoRepository.buscarComSpecs(specs,pageable);
        boolean hasNext = medicos.size() > limit;
        List<MedicoResponse> response = medicos.stream()
                .limit(limit)
                .map(MedicoResponse::of)
                .toList();
        return new PaginacaoResponse<>(
                response,
                hasNext ? response.getLast().id() : null,
                hasNext
        );
    }
}
