package com.example.SpringBootData_project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.example.SpringBootData_project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    public Optional<User> findByEmail(String email);
    public Optional<User> findByFirstName(String firstName);

    
}
