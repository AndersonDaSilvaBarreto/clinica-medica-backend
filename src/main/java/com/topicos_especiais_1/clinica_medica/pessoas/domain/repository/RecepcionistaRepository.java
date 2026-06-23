package com.topicos_especiais_1.clinica_medica.pessoas.domain.repository;

import com.topicos_especiais_1.clinica_medica.pessoas.domain.entity.Recepcionista;
import com.topicos_especiais_1.clinica_medica.shared.domain.valueobject.Cpf;

import java.util.List;
import java.util.UUID;

public interface RecepcionistaRepository {
    Recepcionista salvar(Recepcionista recepcionista);
    Recepcionista buscarPorId(UUID recepcionistaId);
    Recepcionista buscarPorCpf(Cpf cpf);
    Recepcionista buscarPorIdComDatalhes(UUID recepcionistaId);
    Recepcionista buscarPorCpfComDetalhes(Cpf cpf);
    List<Recepcionista> buscaPaginada(UUID cursor, String busca, int limit);
}
