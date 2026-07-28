package com.example.SpringBootData_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SignInResponse {
    private String token;
    private String email;
    private List<String> roles;
}
