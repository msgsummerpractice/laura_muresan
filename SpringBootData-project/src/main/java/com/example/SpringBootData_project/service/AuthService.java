package com.example.SpringBootData_project.service;

import com.example.SpringBootData_project.dto.request.MfaVerifyRequest;
import com.example.SpringBootData_project.dto.request.RegisterRequest;
import com.example.SpringBootData_project.dto.request.SignInRequest;
import com.example.SpringBootData_project.dto.response.MfaChallengeResponse;
import com.example.SpringBootData_project.dto.response.SignInResponse;
import com.example.SpringBootData_project.exception.DuplicateEmailException;
import com.example.SpringBootData_project.model.Role;
import com.example.SpringBootData_project.model.RoleType;
import com.example.SpringBootData_project.model.User;
import com.example.SpringBootData_project.repository.RoleRepository;
import com.example.SpringBootData_project.repository.UserRepository;
import com.example.SpringBootData_project.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MfaService mfaService;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already registered: " + request.getEmail());
        }

        RoleType roleType;
        try {
            roleType = RoleType.valueOf(
                    request.getRole() != null ? request.getRole().toUpperCase() : "USER");
        } catch (IllegalArgumentException e) {
            roleType = RoleType.USER;
        }
        final RoleType resolvedRole = roleType;

        Role userRole = roleRepository.findByName(resolvedRole)
                .orElseThrow(() -> new RuntimeException(
                        resolvedRole + " role not found. Make sure it exists in the roles table."));

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMfaEnabled(request.isMfaEnabled());
        user.setCreatedAt(LocalDateTime.now());
        user.setRoles(Set.of(userRole));

        return userRepository.save(user);
    }

    @Override
    public Object login(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> roles = user.getRoles().stream()
                .map(r -> r.getName().name())
                .collect(Collectors.toList());

        if (user.isMfaEnabled()) {
            mfaService.generateOtp(request.getEmail());
            String challengeToken = jwtTokenProvider.generateMfaChallengeToken(request.getEmail());
            return new MfaChallengeResponse(challengeToken);
        }

        String token = jwtTokenProvider.generateToken(request.getEmail(), roles);
        return new SignInResponse(token, request.getEmail(), roles);
    }

    @Override
    public SignInResponse verifyMfa(MfaVerifyRequest request) {
        String challengeToken = request.getChallengeToken();

        if (!jwtTokenProvider.validateToken(challengeToken)) {
            throw new IllegalArgumentException("Invalid or expired challenge token");
        }
        if (!jwtTokenProvider.isMfaChallengeToken(challengeToken)) {
            throw new IllegalArgumentException("Provided token is not an MFA challenge token");
        }

        String email = jwtTokenProvider.extractEmail(challengeToken);
        mfaService.validateOtp(email, request.getOtpCode());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> roles = user.getRoles().stream()
                .map(r -> r.getName().name())
                .collect(Collectors.toList());

        String token = jwtTokenProvider.generateToken(email, roles);
        return new SignInResponse(token, email, roles);
    }
}
