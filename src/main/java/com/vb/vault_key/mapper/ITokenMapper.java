package com.vb.vault_key.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vb.vault_key.database.entity.TokenModel;
import com.vb.vault_key.dto.request.TokenRequestDTO;
import com.vb.vault_key.dto.response.TokenResponseDTO;

@Mapper(componentModel = "spring")
public interface ITokenMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "geradoEm", ignore = true)
    @Mapping(target = "usos", ignore = true)
    @Mapping(target = "expiraEm", ignore = true)
    TokenModel toEntity(TokenRequestDTO dto);

    TokenResponseDTO toResponse(TokenModel model);
}
