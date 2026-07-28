package com.example.SpringBootData_project.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MfaVerifyRequest {

    @NotBlank(message = "Challenge token is required")
    private String challengeToken;

    @NotBlank(message = "OTP code is required")
    private String otpCode;
}
