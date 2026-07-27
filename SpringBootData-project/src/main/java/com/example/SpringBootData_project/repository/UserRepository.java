package com.example.SpringBootData_project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.example.SpringBootData_project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String username);
}
