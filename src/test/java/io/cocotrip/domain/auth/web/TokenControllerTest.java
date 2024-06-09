package io.cocotrip.domain.auth.web;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import autoparams.AutoSource;
import io.cocotrip.domain.auth.application.TokenService;
import io.cocotrip.domain.auth.application.dto.CreateAccessTokenRequest;
import io.cocotrip.global.config.jwt.TokenProvider;

@WebMvcTest(TokenController.class)
public class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private TokenService tokenService;

    @DisplayName("createNewAccessToken : 새로운 엑세스 토큰을 생성한다.")
    @ParameterizedTest
    @AutoSource
    public void createNewAccessToken(final CreateAccessTokenRequest request) throws Exception {
        // given
        final String response = "accessToken";

        given(tokenService.createNewAccessToken(any())).willReturn(response);

        // when
        final var result = mockMvc.perform(
            post("/api/token")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf())
        );

        // then
        result.andExpect(status().isCreated());
    }
}