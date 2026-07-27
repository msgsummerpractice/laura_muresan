package com.example.SpringBootData_project.model;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity(name = "users")
public class User {
    @Id
    @Column(name = "users_id")
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private float salary;
}
