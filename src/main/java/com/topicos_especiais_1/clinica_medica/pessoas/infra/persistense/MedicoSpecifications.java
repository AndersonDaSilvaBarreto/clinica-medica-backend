package com.topicos_especiais_1.clinica_medica.pessoas.infra.persistense;

import com.topicos_especiais_1.clinica_medica.identidade.domain.entity.Usuario;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Especialidade;
import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Medico;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class MedicoSpecifications {
    public static Specification<Medico> porAtivoUsuario(Boolean ativo) {
        return (root, _, criteriaBuilder) ->
                ativo != null ? criteriaBuilder.equal(root.get("usuario").get("ativo"),ativo) : null;
    }
    public static Specification<Medico> porEspecialidadeId(UUID especialidadeId) {
        return (root, _, criteriaBuilder) -> {
            if (especialidadeId == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Medico, Especialidade> joinEspecialidades = root.join("especialidades");
            return criteriaBuilder.equal(joinEspecialidades.get("id"),especialidadeId);
        };
    }

    public static Specification<Medico> porNomeMedico(String nome) {
        return (root, query, cb) -> {
            if(nome == null || nome.isBlank()) {
                return cb.conjunction();
            }
            Join<Medico, Usuario> joinUsuario = root.join("usuario");
            String termoBusca = "%" + nome.trim().toLowerCase() + "%";
            return cb.like(cb.lower(joinUsuario.get("nome").get("value")),termoBusca);
        };
    }

    public static Specification<Medico> idMaiorQue(UUID medicoId) {
        return (root, query, cb) ->
                medicoId != null? cb.greaterThan(root.get("id"),medicoId): cb.conjunction();
    }
}
