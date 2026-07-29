package com.example.SpringBootData_project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import com.example.SpringBootData_project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    public Optional<User> findByEmail(String email);
    public Optional<User> findByFirstName(String firstName);

    public List<User> findTop10ByOrderByFirstNameAsc();

    @Query("SELECT COUNT(u) FROM users u")
    public long countUsers();
}
