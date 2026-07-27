package com.example.SpringBootData_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.SpringBootData_project.model.User;
import com.example.SpringBootData_project.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

@InjectMocks
   private UserService userService;
    @Mock
    private UserRepository userRepository;
    User user = new User("1", "John", "Doe", "john.doe@example.com", 50000);

    @Test
    public void findByEmail_returnsUser_WhenEmailExists() {
        
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        User result = userService.findByEmail("john.doe@example.com");
        assertEquals(result, user);
    }
    
    @Test 
    public void findByUsername_returnsUser_WhenUsernameExists() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        User result = userService.findByUsername("John");
        assertEquals(result, user);
    }

    @Test
    public void addUser_savesUser_WhenUserIsValid() {
        userService.addUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void updateUser_savesUser_WhenUserIsValid() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        userService.updateUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void deleteUser_deletesUser_WhenIdExists() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void getUserById_returnsUser_WhenIdExists() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        User result = userService.getUserById(1L);
        assertEquals(result, user);
    }

    @Test
    public void getAllUsers_returnsListOfUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(java.util.Arrays.asList(user));
        java.util.List<User> result = userService.getAllUsers();
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), user);
    }

}
