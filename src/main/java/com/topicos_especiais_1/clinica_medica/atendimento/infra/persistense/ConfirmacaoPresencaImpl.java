package com.topicos_especiais_1.clinica_medica.atendimento.infra.persistense;

import com.topicos_especiais_1.clinica_medica.atendimento.domain.entity.ConfirmacaoPresenca;
import com.topicos_especiais_1.clinica_medica.atendimento.domain.repository.ConfirmacaoPresencaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ConfirmacaoPresencaImpl implements ConfirmacaoPresencaRepository {
    
    private final SpringDataConfirmacaoPresenca springDataConfirmacaoPresenca;

    @Override
    public ConfirmacaoPresenca salvar(ConfirmacaoPresenca confirmacaoPresenca) {
        return springDataConfirmacaoPresenca.save(confirmacaoPresenca);
    }

    @Override
    public boolean existePorConsultaId(UUID consultaId) {
        Specification<ConfirmacaoPresenca> specs = Specification.where(ConfirmacaoPresencaSpecifications.porConsultaId(consultaId));
        return springDataConfirmacaoPresenca.exists(specs);
    }
}
