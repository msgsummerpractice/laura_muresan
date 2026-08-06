package com.example.SpringBootData_project.dataseeder;
import jakarta.annotation.PostConstruct;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.SpringBootData_project.model.Role;
import com.example.SpringBootData_project.model.RoleType;
import com.example.SpringBootData_project.model.User;
import com.example.SpringBootData_project.repository.RoleRepository;
import com.example.SpringBootData_project.repository.UserRepository;
@Component
public class dataSeeder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void seedData() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName(RoleType.ADMIN);
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName(RoleType.USER);
            roleRepository.save(userRole);
        }
        if (userRepository.count() == 0) {
            User adminUser = new User();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setEmail("admin@example.com");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRoles(roleRepository.findByName(RoleType.ADMIN)
                    .map(Set::of)
                    .orElseThrow(() -> new RuntimeException("Admin role not found.")));
            userRepository.save(adminUser);
        }
    }
}