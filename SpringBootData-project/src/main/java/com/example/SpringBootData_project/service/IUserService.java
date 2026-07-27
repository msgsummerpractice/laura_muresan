package com.example.SpringBootData_project.service;

import com.example.SpringBootData_project.model.User;
import java.util.List;

public interface IUserService {

    public User findByEmail(String email);
    public User findByUsername(String username);
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(Long id);
    public User getUserById(Long id);
    public List<User> getAllUsers();
    
}
