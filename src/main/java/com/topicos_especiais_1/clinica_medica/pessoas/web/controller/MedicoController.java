package com.topicos_especiais_1.clinica_medica.pessoas.web.controller;

import com.topicos_especiais_1.clinica_medica.identidade.infra.security.UsuarioAutenticado;
import com.topicos_especiais_1.clinica_medica.pessoas.application.usecase.*;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.AtualizarMedicoRequest;
import com.topicos_especiais_1.clinica_medica.pessoas.web.dto.MedicoResponse;
import com.topicos_especiais_1.clinica_medica.shared.web.dto.PaginacaoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {
    private final BuscarMedicoPorUsuarioIdUseCase buscarMedicoPorUsuarioIdUseCase;    
    private final BuscaMedicoPaginadoUseUse buscaMedicoPaginadoUseUse;
    private final BuscarMedicoComEspecialidadesUseCase buscarMedicoComEspecialidadesUseCase;
    private final AtualizarMedicoUseCase atualizarMedicoUseCase;
    private final BuscarMedicoPorEspecialidadeUseCase buscarMedicoPorEspecialidadeUseCase;
    @GetMapping
    public ResponseEntity<PaginacaoResponse<MedicoResponse>> buscaPaginada(
            @RequestParam(name = "cursor", required = false)UUID cursor,
            @RequestParam(name = "busca",required = false) String busca,
            @RequestParam(name = "limit",required = false,defaultValue = "10") int limit
            ) {
        var response = buscaMedicoPaginadoUseUse.execute(cursor,busca,limit);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
    @GetMapping("/busca")
    public ResponseEntity<PaginacaoResponse<MedicoResponse>> buscarPorEspecialidade(
            @RequestParam(required = false) UUID especialidadeId,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false,defaultValue = "10") int limit,
            @RequestParam(required = false) UUID cursor
    ){
        PaginacaoResponse<MedicoResponse> response = buscarMedicoPorEspecialidadeUseCase.execute(
                especialidadeId,
                cursor,
                nome,
                limit
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/me") 
        public ResponseEntity<MedicoResponse> buscarMedicoLogado(
                @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado
        ) {
        var response = buscarMedicoPorUsuarioIdUseCase.execute(usuarioAutenticado.usuario().getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
        }
    @GetMapping("/{medicoId}")
    public ResponseEntity<MedicoResponse> buscarPorIdComEspecialidades(
            @PathVariable UUID medicoId
    ) {
        var response = buscarMedicoComEspecialidadesUseCase.execute(medicoId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @PatchMapping("/{medicoId}")
    public ResponseEntity<Void>  atualizarMedico(
            @PathVariable UUID medicoId,
            @AuthenticationPrincipal UsuarioAutenticado usuarioAutenticado,
            @RequestBody @Valid AtualizarMedicoRequest request
            ) {
        atualizarMedicoUseCase.execute(medicoId,usuarioAutenticado,request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }
}
