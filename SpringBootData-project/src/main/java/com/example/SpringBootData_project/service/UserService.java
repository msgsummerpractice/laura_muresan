package com.example.SpringBootData_project.service;
import com.example.SpringBootData_project.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.SpringBootData_project.dto.request.UserRequest;
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

    public User addUser(UserRequest user) {
        User userToSave = new User();
        userToSave.setFirstName(user.getFirstName());
        userToSave.setLastName(user.getLastName());
        userToSave.setEmail(user.getEmail());
        userToSave.setSalary(user.getSalary());
        return userRepository.save(userToSave);
    }

    public User updateUser(Long id,User user) {
        User userToSave = userRepository.findById(user.getId()).orElse(null);

        userToSave.setFirstName(user.getFirstName());
        userToSave.setLastName(user.getLastName());
        userToSave.setEmail(user.getEmail());
        userToSave.setSalary(user.getSalary());
        return userRepository.save(userToSave);
    }

    public User deleteUser(Long id) {
        User userToDelete = userRepository.findById(id).orElse(null);
        if (userToDelete != null) {
            userRepository.deleteById(id);
        }
        return userToDelete;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User updateEmail(User user, String newEmail) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {
            if (newEmail != null) {
                existingUser.setEmail(newEmail);
                return userRepository.save(existingUser);
            }
        }
        return existingUser;
    }
}
