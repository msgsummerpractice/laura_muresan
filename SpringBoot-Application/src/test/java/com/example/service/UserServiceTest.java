package com.example.service;

import org.junit.jupiter.api.Test;
 import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import com.example.repository.UserRepository;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

@InjectMocks
   private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetUserCount_returnsZero_WhenListIsEmpty() {
        Mockito.when(userRepository.getAll()).thenReturn(new java.util.ArrayList<>());
        int count = userService.getUserCount();
        assert(count == 0);
    }
    
}