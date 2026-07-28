package com.example.SpringBootData_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

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

    @Test
    public void findByEmail_returnsUser_WhenEmailExists() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", 50000);
        Mockito.when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        User result = userService.findByEmail("john.doe@example.com");
        assertEquals(result, user);
    }
    
    @Test 
    public void findByFirstName_returnsUser_WhenFirstNameExists() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", 50000);
        Mockito.when(userRepository.findByFirstName("John")).thenReturn(Optional.of(user));
        User result = userService.findByFirstName("John");
        assertEquals(result, user);
    }

    @Test
    public void addUser_savesUser_WhenUserIsValid() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", 50000);
        userService.addUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void updateUser_savesUser_WhenUserIsValid() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", 50000);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        userService.updateUser(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void deleteUser_deletesUser_WhenIdExists() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", 50000);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        userService.deleteUser(1);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1);
    }

    @Test
    public void getUserById_returnsUser_WhenIdExists() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", 50000);
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        User result = userService.getUserById(1);
        assertEquals(result, user);
    }

    @Test
    public void getAllUsers_returnsListOfUsers() {
        User user = new User(1, "John", "Doe", "john.doe@example.com", 50000);
        Mockito.when(userRepository.findAll()).thenReturn(java.util.Arrays.asList(user));
        java.util.List<User> result = userService.getAllUsers();
        assertEquals(result.size(), 1);
        assertEquals(result.get(0), user);
    }

}
