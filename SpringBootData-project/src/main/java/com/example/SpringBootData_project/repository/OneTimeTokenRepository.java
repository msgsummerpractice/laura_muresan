package com.example.SpringBootData_project.repository;

import com.example.SpringBootData_project.model.OneTimeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OneTimeTokenRepository extends JpaRepository<OneTimeToken, Long> {

    Optional<OneTimeToken> findByEmailAndCodeAndUsedFalse(String email, String code);
    void deleteByEmail(String email);
}
