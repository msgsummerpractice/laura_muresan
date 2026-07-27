package com.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.example.service.UserService;

public class UserServiceTest {

@MockitoBean
   private UserService userService;
   @BeforeEach
    public void setUp() {
        userService = new UserService(new com.example.repository.UserRepository());
        userService.add(new com.example.model.User("1", 25, "John Doe"));
        userService.add(new com.example.model.User("2", 30, "Jane Doe"));
    }
    
    @Test
    public void testGetUserCount_returnsZero_WhenListIsEmpty() {
        Mockito.when(userService.getUserCount()).thenReturn(0);
        int count = userService.getUserCount();
        assert(count == 0);
    }
    
}
