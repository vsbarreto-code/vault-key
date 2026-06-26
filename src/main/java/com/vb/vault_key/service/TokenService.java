package com.vb.vault_key.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vb.vault_key.database.repository.ITokenRepository;
import com.vb.vault_key.dto.request.TokenRequestDTO;
import com.vb.vault_key.dto.response.TokenResponseDTO;
import com.vb.vault_key.exception.ConflitoException;
import com.vb.vault_key.exception.NaoDisponivelException;
import com.vb.vault_key.exception.NaoEncontradoException;
import com.vb.vault_key.mapper.ITokenMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final ITokenRepository repository;
    private final ITokenMapper mapper;

    @Transactional
    public TokenResponseDTO gerar(TokenRequestDTO dto) {
        LocalDateTime geradoEm = LocalDateTime.now();
        LocalDateTime expiraEm = geradoEm
            .plusMinutes(dto.duracaoMinutos());

        UUID random = UUID.randomUUID();
        String token = "VK-" + random + "-" + dto.nivelAcesso();

        var entity = mapper.toEntity(dto);
        entity.setToken(token);
        entity.setGeradoEm(geradoEm);
        entity.setExpiraEm(expiraEm);

        return mapper.toResponse(repository.save(entity));
    }

    public String validar(String token) {

        var tk = repository.findByToken(token)
                           .orElseThrow(() -> new NaoEncontradoException("Token não encontrado!"));


        LocalDateTime dt = LocalDateTime.now();
        if (dt.isAfter(tk.getExpiraEm())) {
            throw new NaoDisponivelException("Token expirado!");
        }

        if (tk.getUsoUnico() && tk.getUsos() >= 1) {
            throw new ConflitoException("Uso superior ao previsto para o Token!");
        }


        tk.setUsos(tk.getUsos() + 1);
        repository.save(tk);
        return "Token validado com sucesso!";

    }


}
