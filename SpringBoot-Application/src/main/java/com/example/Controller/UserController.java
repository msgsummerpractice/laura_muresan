package com.example.controller;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.User;
import com.example.service.UserService;



@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired private UserService userService;

    @Value("${app.user.defaultName}")
    private String defaultName;
    @GetMapping("/users/default")
    public String getDefaultUser() {
        return "Default name is " + defaultName;
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userService.getAll();
    }

    @PostMapping
    public void addUser(@Valid @RequestBody User user) {
        logger.info("Adding new user: {}", user);
        userService.add(user);
    }


}