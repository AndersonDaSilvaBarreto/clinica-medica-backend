package com.topicos_especiais_1.clinica_medica.consulta.infra.persistence;

import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Consulta;
import com.topicos_especiais_1.clinica_medica.consulta.domain.entity.Prontuario;
import com.topicos_especiais_1.clinica_medica.consulta.domain.repository.ProntuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProntuarioRepositoryImpl implements ProntuarioRepository {
    private final SpringDataProntuario springDataProntuario;
    @Override
    public Prontuario salvar(Prontuario prontuario) {
        return springDataProntuario.save(prontuario);
    }

    @Override
    public boolean existePorConsulta(Consulta consulta) {
        return springDataProntuario.existsByConsulta(consulta);
    }
}
