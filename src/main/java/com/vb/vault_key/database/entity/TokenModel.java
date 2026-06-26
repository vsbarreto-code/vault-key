package com.vb.vault_key.database.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vb.vault_key.database.entity.enums.NivelAcessoEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_tokens")
@Data
public class TokenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String destinatario;
    private NivelAcessoEnum nivelAcesso;
    private Integer duracaoMinutos;
    private Boolean usoUnico;
    private String token;
    private LocalDateTime geradoEm;
    private Integer usos = 0;
    private LocalDateTime expiraEm;
}
