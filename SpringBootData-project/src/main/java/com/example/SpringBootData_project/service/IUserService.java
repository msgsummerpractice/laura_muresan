package com.example.SpringBootData_project.service;

import com.example.SpringBootData_project.dto.request.UpdateUserRequest;
import com.example.SpringBootData_project.dto.request.UserRequest;
import com.example.SpringBootData_project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface IUserService {

    public User findByEmail(String email);
    public User findByFirstName(String firstName);
    public User addUser(UserRequest user);
    public User updateUser(Long id, UserRequest user);
    public User patchUser(Long id, UpdateUserRequest user);
    public void deleteUser(Long id);
    public User getUserById(Long id);
    public Page<User> getAllUsers(@NonNull Pageable pageable);
    
}
