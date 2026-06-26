package com.vb.vault_key.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.vb.vault_key.dto.response.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> naoEncontrado(NaoEncontradoException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Dados Indisponíveis",
            List.of(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(response);
    }

    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<ErrorResponseDTO> conflito(ConflitoException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            "Conflito de Dados",
            List.of(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(response);
    }

    @ExceptionHandler(NaoDisponivelException.class)
    public ResponseEntity<ErrorResponseDTO> conflito(NaoDisponivelException ex) {
        ErrorResponseDTO response = new ErrorResponseDTO(
            LocalDateTime.now(),
            HttpStatus.GONE.value(),
            "Recurso não está mais disponível",
            List.of(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.GONE)
                             .body(response);
    }
}
