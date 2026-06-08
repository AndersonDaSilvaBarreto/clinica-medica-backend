package com.topicos_especiais_1.clinica_medica.shared.infra.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public <T> void salvar(String chave, T dados, Duration expiracao) {
        try {
            String json = objectMapper.writeValueAsString(dados);
            redisTemplate.opsForValue().set(chave,json,expiracao);
        } catch (JacksonException e) {
            log.error("Erro ao salvar no cache para chave {}: {}", chave, e.getMessage());
            throw new RuntimeException(e);

        }
    }
    public <T> Optional<T> buscar(String chave, Class<T> tipo) {
        try {
            String json = redisTemplate.opsForValue().get(chave);
            if (json == null) return Optional.empty();
            return Optional.of(objectMapper.readValue(json, tipo));
        } catch (Exception e) {
            log.error("Erro ao buscar no cache para chave {}: {}", chave, e.getMessage());
            return Optional.empty();
        }
    }

    public void deletar(String chave) {
        redisTemplate.delete(chave);
    }

    public boolean existe(String chave) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(chave));
    }
}
