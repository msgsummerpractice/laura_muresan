package com.example.SpringBootData_project.controller;

import com.example.SpringBootData_project.dto.request.UpdateUserRequest;
import com.example.SpringBootData_project.dto.request.UserRequest;
import com.example.SpringBootData_project.model.Role;
import com.example.SpringBootData_project.model.RoleType;
import com.example.SpringBootData_project.model.User;
import com.example.SpringBootData_project.repository.RoleRepository;
import com.example.SpringBootData_project.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;

    private User testUser;

    @BeforeEach
    void setupDatabase() {
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

        User u = new User();
        u.setFirstName("Existing");
        u.setLastName("User");
        u.setEmail("existing@test.com");
        u.setPassword("hashed_password");
        u.setCreatedAt(LocalDateTime.now());
        testUser = userRepository.save(u);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAllUsers_returns200WithContent_whenAdmin() throws Exception {
        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void getAllUsers_returns200_whenUser() throws Exception {
        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void getAllUsers_redirectsToLogin_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/users/all"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser
    void getUserById_returns200_whenUserExists() throws Exception {
        mockMvc.perform(get("/users/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("existing@test.com"));
    }

    @Test
    @WithMockUser
    void getUserById_returns404_whenUserDoesNotExist() throws Exception {
        mockMvc.perform(get("/users/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserById_redirectsToLogin_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/users/" + testUser.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void addUser_returns201_whenRequestIsValid() throws Exception {
        UserRequest req = new UserRequest("New", "User", "new@test.com", 2500f, "password12");

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("new@test.com"))
                .andExpect(jsonPath("$.firstName").value("New"));
    }

    @Test
    @WithMockUser
    void addUser_returns400_whenFirstNameIsBlank() throws Exception {
        UserRequest req = new UserRequest("", "User", "blank@test.com", 1000f, "password12");

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void addUser_returns400_whenEmailIsInvalid() throws Exception {
        UserRequest req = new UserRequest("First", "Last", "not-an-email", 1000f, "password12");

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void addUser_returns400_whenPasswordIsTooShort() throws Exception {
        UserRequest req = new UserRequest("First", "Last", "short@test.com", 1000f, "pass");

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void addUser_returns409_whenEmailAlreadyExists() throws Exception {
        UserRequest req = new UserRequest("Dup", "User", "existing@test.com", 1000f, "password12");

        mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteUser_returns204_whenAdmin() throws Exception {
        mockMvc.perform(delete("/users/delete/" + testUser.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void deleteUser_returns403_whenUser() throws Exception {
        mockMvc.perform(delete("/users/delete/" + testUser.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteUser_redirectsToLogin_whenUnauthenticated() throws Exception {
        mockMvc.perform(delete("/users/delete/" + testUser.getId()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteUser_returns404_whenUserDoesNotExist() throws Exception {
        mockMvc.perform(delete("/users/delete/99999"))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser
    void updateUser_returns200_whenRequestIsValid() throws Exception {
        UserRequest req = new UserRequest("Updated", "Name", "updated@test.com", 5000f, "newpass123");

        mockMvc.perform(put("/users/update/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.email").value("updated@test.com"));
    }

    @Test
    @WithMockUser
    void updateUser_returns404_whenUserDoesNotExist() throws Exception {
        UserRequest req = new UserRequest("X", "Y", "x@test.com", 1f, "password12");

        mockMvc.perform(put("/users/update/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void updateUser_returns400_whenEmailIsInvalid() throws Exception {
        UserRequest req = new UserRequest("X", "Y", "bad-email", 1000f, "password12");

        mockMvc.perform(put("/users/update/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser
    void patchUser_returns200_whenPartialUpdateIsValid() throws Exception {
        UpdateUserRequest req = new UpdateUserRequest();
        req.setFirstName("Patched");

        mockMvc.perform(patch("/users/patch/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Patched"));
    }

    @Test
    @WithMockUser
    void patchUser_returns404_whenUserDoesNotExist() throws Exception {
        UpdateUserRequest req = new UpdateUserRequest();
        req.setFirstName("Ghost");

        mockMvc.perform(patch("/users/patch/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void patchUser_returns400_whenEmailIsInvalid() throws Exception {
        UpdateUserRequest req = new UpdateUserRequest();
        req.setEmail("not-valid");

        mockMvc.perform(patch("/users/patch/" + testUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }
}
