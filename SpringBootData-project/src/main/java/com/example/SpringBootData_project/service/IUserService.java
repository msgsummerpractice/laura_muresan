package com.example.SpringBootData_project.service;

import com.example.SpringBootData_project.model.User;
import java.util.List;

public interface IUserService {

    public User findByEmail(String email);
    public User findByFirstName(String firstName);
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(Integer id);
    public User getUserById(Integer id);
    public List<User> getAllUsers();
    
}
