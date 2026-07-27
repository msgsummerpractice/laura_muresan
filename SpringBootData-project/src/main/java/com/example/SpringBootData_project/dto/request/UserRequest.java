package com.example.SpringBootData_project.dto.request;

import com.example.SpringBootData_project.dto.request.annotations.Email;
import com.example.SpringBootData_project.dto.request.annotations.NotBlank;
import com.example.SpringBootData_project.dto.request.annotations.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @Positive(message = "Salary must be a positive number")
    private float salary;



    
}
