package com.example.SpringBootData_project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MfaChallengeResponse {

    private boolean mfaRequired;
    private String challengeToken;
    private String otpCode;
}
