package com.example.service;

import java.util.List;

import com.example.model.User;

public interface IUserService {
    public int getUserCount();
    public List<User> getAll();
    public void add(User user);
}