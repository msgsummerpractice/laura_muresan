package com.example.SpringBootData_project.controller;
import org.springframework.http.MediaType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.SpringBootData_project.dto.request.UserRequest;
import com.example.SpringBootData_project.dto.response.UserResponse;
import com.example.SpringBootData_project.model.User;
import com.example.SpringBootData_project.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired UserService userService;
    
    @GetMapping(value = "/all",  produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<List<User>> getAllUsers() {
        
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping(value = "/add", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest request) {
        UserResponse response = toDto(userService.addUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResponse response = toDto(userService.getUserById(id));
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        UserResponse response = toDto(updatedUser);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping(value = "/delete/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        UserResponse response = toDto(userService.getUserById(id));
        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/updateEmail", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<UserResponse> updateEmail(@RequestBody User user, @RequestBody String newEmail) {
        userService.updateEmail(user, newEmail);
        UserResponse response = toDto(userService.getUserById(user.getId()));
        return ResponseEntity.ok(response);
    }

    private UserResponse toDto(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setSalary(user.getSalary());
        return response;
    }

}
