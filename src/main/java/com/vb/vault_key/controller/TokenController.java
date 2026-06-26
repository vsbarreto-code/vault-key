package com.vb.vault_key.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vb.vault_key.dto.TokenValidarDTO;
import com.vb.vault_key.dto.request.TokenRequestDTO;
import com.vb.vault_key.dto.response.TokenResponseDTO;
import com.vb.vault_key.service.TokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/api/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService service;

    @PostMapping
    public ResponseEntity<TokenResponseDTO> gerar(@RequestBody TokenRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(service.gerar(dto));
    }

    @PostMapping("/validar")
    public ResponseEntity<String> validar(@RequestBody TokenValidarDTO dto) {
        return ResponseEntity.ok(service.validar(dto.token()));

    }
}
