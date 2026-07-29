package com.example.SpringBootData_project.controller;

import com.example.SpringBootData_project.dto.request.RegisterRequest;
import com.example.SpringBootData_project.dto.request.SignInRequest;
import com.example.SpringBootData_project.model.Role;
import com.example.SpringBootData_project.model.RoleType;
import com.example.SpringBootData_project.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired RoleRepository roleRepository;

    @BeforeEach
    void seedRoles() {
        if (roleRepository.findByName(RoleType.USER).isEmpty()) {
            Role r = new Role();
            r.setName(RoleType.USER);
            roleRepository.save(r);
        }
        if (roleRepository.findByName(RoleType.ADMIN).isEmpty()) {
            Role r = new Role();
            r.setName(RoleType.ADMIN);
            roleRepository.save(r);
        }
    }

    @Test
    void register_returnsCreated_whenRequestIsValid() throws Exception {
        RegisterRequest req = new RegisterRequest(
                "John", "Doe", "john@test.com", "password123", false, "USER");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("john@test.com"))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void register_returnsCreated_withAdminRole() throws Exception {
        RegisterRequest req = new RegisterRequest(
                "Admin", "User", "admin@test.com", "adminpass1", false, "ADMIN");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void register_returnsConflict_whenEmailAlreadyExists() throws Exception {
        RegisterRequest req = new RegisterRequest(
                "John", "Doe", "dup@test.com", "password123", false, "USER");
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }

    @Test
    void register_returnsBadRequest_whenEmailInvalid() throws Exception {
        String body = """
                {"firstName":"John","lastName":"Doe","email":"not-an-email",
                 "password":"password123","mfaEnabled":false,"role":"USER"}
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_returnsBadRequest_whenFirstNameIsBlank() throws Exception {
        String body = """
                {"firstName":"","lastName":"Doe","email":"blank@test.com",
                 "password":"password123","mfaEnabled":false,"role":"USER"}
                """;

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_returnsOkWithToken_whenCredentialsAreValid() throws Exception {
        RegisterRequest reg = new RegisterRequest(
                "Jane", "Doe", "jane@test.com", "password123", false, "USER");
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reg)));

        SignInRequest login = new SignInRequest("jane@test.com", "password123");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value("jane@test.com"))
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    void login_returnsOkWithMfaChallenge_whenMfaIsEnabled() throws Exception {

        RegisterRequest reg = new RegisterRequest(
                "Mfa", "User", "mfa@test.com", "password123", true, "USER");
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reg)));

        SignInRequest login = new SignInRequest("mfa@test.com", "password123");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mfaRequired").value(true))
                .andExpect(jsonPath("$.challengeToken").isNotEmpty())
                .andExpect(jsonPath("$.otpCode").isNotEmpty());
    }

    @Test
    void login_returnsUnauthorized_whenPasswordIsWrong() throws Exception {
        RegisterRequest reg = new RegisterRequest(
                "Jane", "Doe", "jane2@test.com", "password123", false, "USER");
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reg)));

        SignInRequest login = new SignInRequest("jane2@test.com", "wrongpassword");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_returnsUnauthorized_whenUserDoesNotExist() throws Exception {
        SignInRequest login = new SignInRequest("nobody@test.com", "somepass");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void verifyMfa_returnsBadRequest_whenChallengeTokenIsInvalid() throws Exception {
        String body = """
                {"challengeToken":"invalid.token.here","otpCode":"123456"}
                """;

        mockMvc.perform(post("/api/auth/verify-mfa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}
