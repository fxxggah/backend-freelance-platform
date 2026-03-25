package com.freelance.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelance.platform.config.security.JwtService;
import com.freelance.platform.domain.enums.UserType;
import com.freelance.platform.domain.model.User;
import com.freelance.platform.dto.request.LoginRequest;
import com.freelance.platform.service.UserService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules();

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("Deve realizar login com sucesso")
    void deveRealizarLoginComSucesso() throws Exception {

        LoginRequest request = LoginRequest.builder()
                .email("gabriel@gmail.com")
                .password("123456")
                .build();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                request.getEmail(),
                request.getPassword(),
                new ArrayList<>()
        );

        User user = User.builder()
                .id(1L)
                .name("Gabriel")
                .email(request.getEmail())
                .userType(UserType.EMPLOYER)
                .build();

        when(userDetailsService.loadUserByUsername(request.getEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(request.getEmail())).thenReturn("fake-jwt-token");
        when(userService.getEntityByEmail(request.getEmail())).thenReturn(user);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.type").value("Bearer"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.email").value(request.getEmail()))
                .andExpect(jsonPath("$.name").value("Gabriel"))
                .andExpect(jsonPath("$.role").value("EMPLOYER"));
    }
}