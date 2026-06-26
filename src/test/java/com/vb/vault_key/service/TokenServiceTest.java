package com.vb.vault_key.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.vb.vault_key.database.entity.TokenModel;
import com.vb.vault_key.database.entity.enums.NivelAcessoEnum;
import com.vb.vault_key.database.repository.ITokenRepository;
import com.vb.vault_key.dto.request.TokenRequestDTO;
import com.vb.vault_key.dto.response.TokenResponseDTO;
import com.vb.vault_key.mapper.ITokenMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private ITokenRepository repository;

    @Mock
    private ITokenMapper mapper;

    @Captor
    private ArgumentCaptor<TokenModel> tokenCaptor;

    @InjectMocks
    private TokenService service;

    @Nested
    class PostToken {
        @Test
        @DisplayName("Criando Token com sucesso")
        void criarTokenComSucesso() {
            //  Arrange -> Organizar
            var request = new TokenRequestDTO("Login", NivelAcessoEnum.ADMIN, 1, Boolean.TRUE);

            var entity = new TokenModel();
            var entitySalvo = new TokenModel();

            LocalDateTime geradoEm = LocalDateTime.now();
            LocalDateTime expiraEm = geradoEm
                .plusMinutes(1);

            UUID random = UUID.randomUUID();
            String token = ("VK" + random + NivelAcessoEnum.ADMIN).replaceAll("\\s+", "");

            entitySalvo.setId(random);
            entitySalvo.setToken(token);
            entitySalvo.setGeradoEm(geradoEm);
            entitySalvo.setExpiraEm(expiraEm);

            var response =
                new TokenResponseDTO(random, "Login", NivelAcessoEnum.ADMIN, 1, Boolean.TRUE, token, geradoEm, 0,
                                     expiraEm);

            doReturn(entity).when(mapper)
                            .toEntity(request);
            doReturn(entitySalvo).when(repository)
                                 .save(entity);
            doReturn(response).when(mapper)
                              .toResponse(entitySalvo);

            //  ACT -> Agir
            var output = service.gerar(request);

            //  ASSERT -> Afirmar
            assertNotNull(output);
            assertEquals(response.id(), output.id());
            verify(repository, times(1)).save(any());
        }

    }

    @Nested
    class PostValidarToken {
        @Test
        @DisplayName("Validar token com sucesso")
        void validarToken() {
            //  ARRANGE -> Organizar
            var token = "VK8b8099f7-3952-434d-8e32-3f07b3183626ADMIN";
            var tokenMock = new TokenModel();
            tokenMock.setToken(token);
            tokenMock.setUsoUnico(false);
            tokenMock.setExpiraEm(LocalDateTime.now()
                                               .plusMinutes(5));

            when(repository.findByToken(token)).thenReturn(Optional.of(tokenMock));

            //ACT -> Agir
            var output = service.validar(token);

            //ASSERT -> Afirmar
            verify(repository, times(1)).save(any());
            assertEquals("Token validado com sucesso!", output);
            assertEquals(1, tokenMock.getUsos(), "O número de usos deve ser incrementado em 1");


        }
    }
}