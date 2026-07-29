package com.example.SpringBootData_project.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String firstName;
    private String lastName;

    @Email(message = "Email is not valid")
    private String email;   

    @Positive(message = "Salary must be a positive number")
    private Float salary;
}
