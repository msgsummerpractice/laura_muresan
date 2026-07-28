package com.example.SpringBootData_project.mapper;
import org.springframework.stereotype.Component;

import com.example.SpringBootData_project.dto.request.UpdateUserRequest;
import com.example.SpringBootData_project.dto.request.UserRequest;
import com.example.SpringBootData_project.dto.response.UserResponse;
import com.example.SpringBootData_project.model.User;

@Component
public class UserMapper {
    public User toEntity(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setSalary(userRequest.getSalary());
        user.setPassword(userRequest.getPassword());
        user.setCreatedAt(java.time.LocalDateTime.now());
        return user;
    }

    public void updateEntityFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setSalary(userRequest.getSalary());
        user.setPassword(userRequest.getPassword());
    }



    public void applyPatch(User user, UpdateUserRequest updateUserRequest) {
        if (updateUserRequest.getFirstName() != null) {
            user.setFirstName(updateUserRequest.getFirstName());
        }
        if (updateUserRequest.getLastName() != null) {
            user.setLastName(updateUserRequest.getLastName());
        }
        if(updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }
    }

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setSalary(user.getSalary());
        response.setCreatedAt(user.getCreatedAt()); 
        return response;
    }
}
