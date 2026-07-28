package com.example.SpringBootData_project.service;

import com.example.SpringBootData_project.dto.request.UpdateUserRequest;
import com.example.SpringBootData_project.dto.request.UserRequest;
import com.example.SpringBootData_project.exception.DuplicateEmailException;
import com.example.SpringBootData_project.exception.ResourceNotFoundException;
import com.example.SpringBootData_project.mapper.UserMapper;
import com.example.SpringBootData_project.model.User;
import com.example.SpringBootData_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName).orElse(null);
    }

    @Override
    public User addUser(UserRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new DuplicateEmailException("A user with email " + request.getEmail() + " already exists");
        });
        User userToSave = userMapper.toEntity(request);
        return userRepository.save(userToSave);
    }

    @Override
    public User updateUser( Long id, UserRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userMapper.updateEntityFromRequest(existing, request);
        return userRepository.save(existing);
    }

    @Override
    public User patchUser(Long id, UpdateUserRequest patch) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userMapper.applyPatch(existing, patch);
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.delete(existing);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @Override
    public Page<User> getAllUsers(@NonNull Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}