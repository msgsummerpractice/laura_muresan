package com.example.SpringBootData_project.service;

import com.example.SpringBootData_project.model.OneTimeToken;
import com.example.SpringBootData_project.repository.OneTimeTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class MfaService {

    @Autowired
    private OneTimeTokenRepository ottRepository;

    private static final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public String generateOtp(String email) {
        ottRepository.deleteByEmail(email);

        String code = String.format("%06d", secureRandom.nextInt(1_000_000));

        OneTimeToken token = new OneTimeToken();
        token.setEmail(email);
        token.setCode(code);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        token.setUsed(false);
        ottRepository.save(token);

        return code;
    }

    @Transactional
    public void validateOtp(String email, String code) {
        OneTimeToken token = ottRepository.findByEmailAndCodeAndUsedFalse(email, code)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or already-used OTP code"));

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("One time token code has expired");
        }
        token.setUsed(true);
        ottRepository.save(token);
    }
}
