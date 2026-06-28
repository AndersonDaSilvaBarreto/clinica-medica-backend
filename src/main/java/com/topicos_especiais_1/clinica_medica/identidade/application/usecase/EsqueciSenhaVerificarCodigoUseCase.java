package com.topicos_especiais_1.clinica_medica.identidade.application.usecase;

import com.topicos_especiais_1.clinica_medica.shared.infra.cache.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EsqueciSenhaVerificarCodigoUseCase {
    private final RedisService redisService;
    
}
