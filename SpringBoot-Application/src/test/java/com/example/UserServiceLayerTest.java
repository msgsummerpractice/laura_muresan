package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Service.UserService;

public class UserServiceLayerTest {

    private UserService userService;
    @BeforeEach
    public void setUp() {
        userService = new UserService(new com.example.Repository.UserRepository());
        userService.add(new com.example.Model.User("1", 25, "John Doe"));
        userService.add(new com.example.Model.User("2", 30, "Jane Doe"));
    }

    @Test
    public void testGetUserCount() {
        int count = userService.getUserCount();
        assert(count == 2);
    }
    
}
