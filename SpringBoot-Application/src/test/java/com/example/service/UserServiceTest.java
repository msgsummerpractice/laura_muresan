package com.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import com.example.service.UserService;
import com.example.repository.UserRepository;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

@InjectMocks
   private UserService userService;
   
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
    }
        
    @Test
    public void testGetUserCount_returnsZero_WhenListIsEmpty() {
        Mockito.when(userService.getUserCount()).thenReturn(0);
        int count = userService.getUserCount();
        assert(count == 0);
    }
    
}
