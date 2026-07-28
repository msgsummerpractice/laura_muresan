package com.example.SpringBootData_project.service;
import com.example.SpringBootData_project.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.SpringBootData_project.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    
    @Autowired UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName).orElse(null);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
