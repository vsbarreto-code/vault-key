package com.vb.vault_key.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
    LocalDateTime timestamp,
    Integer status,
    String erro,
    List<String> mensagem
) {
}
