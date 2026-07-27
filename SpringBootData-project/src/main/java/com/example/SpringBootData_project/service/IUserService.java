package com.example.SpringBootData_project.service;

import com.example.SpringBootData_project.dto.request.UserRequest;
import com.example.SpringBootData_project.model.User;
import java.util.List;

public interface IUserService {

    public User findByEmail(String email);
    public User findByFirstName(String firstName);
    public User addUser(UserRequest user);
    public User updateUser(Long id,User user);
    public User updateEmail(User user, String newEmail);
    public User deleteUser(Long id);
    public User getUserById(Long id);
    public List<User> getAllUsers();
    
}
