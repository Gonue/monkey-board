package com.sh.board.controller;

import com.sh.board.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[view] 컨트롤러 인증")
@Import(SecurityConfig.class)
@WebMvcTest
public class AuthController {

    private final MockMvc mvc;

    public AuthController(@Autowired MockMvc mvc){
        this.mvc = mvc;
    }

    @DisplayName("[view]{GET} 로그인 페이지 - 정상호출")
    @Test
    public void given_whenTryingToLogIn_thenReturnsLogInView() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}
