package com.example.SpringBootData_project.service;

import com.example.SpringBootData_project.dto.request.MfaVerifyRequest;
import com.example.SpringBootData_project.dto.request.RegisterRequest;
import com.example.SpringBootData_project.dto.request.SignInRequest;
import com.example.SpringBootData_project.dto.response.SignInResponse;
import com.example.SpringBootData_project.model.User;

public interface IAuthService {

    User register(RegisterRequest request);
    Object login(SignInRequest request);

    SignInResponse verifyMfa(MfaVerifyRequest request);
}
