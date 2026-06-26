package com.vb.vault_key.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vb.vault_key.database.entity.enums.NivelAcessoEnum;

public record TokenResponseDTO(
    UUID id,
    String destinatario,
    NivelAcessoEnum nivelAcesso,
    Integer duracaoMinutos,
    Boolean usoUnico,
    String token,
    LocalDateTime geradoEm,
    Integer usos,
    LocalDateTime expiraEm
) {
}
