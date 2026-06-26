package com.vb.vault_key.dto.request;

import com.vb.vault_key.database.entity.enums.NivelAcessoEnum;

public record TokenRequestDTO(
    String destinatario,
    NivelAcessoEnum nivelAcesso,
    Integer duracaoMinutos,
    Boolean usoUnico
) {
}
