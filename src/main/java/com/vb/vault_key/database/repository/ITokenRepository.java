package com.vb.vault_key.database.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vb.vault_key.database.entity.TokenModel;

public interface ITokenRepository extends JpaRepository<TokenModel, UUID> {

    Optional<TokenModel> findByToken(String token);
}
