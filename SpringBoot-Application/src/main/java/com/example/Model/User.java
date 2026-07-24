package com.example.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class User {
    @NotNull
    private String Id;
    @NotNull
    @Positive
    private int age;
    @NotNull
    private String name;
    public User(String Id, int age, String name) {
        this.Id = Id;
        this.age = age;
        this.name = name;
    }

    public String getId() {
        return Id;
    }
    public void setId(String Id) {
        this.Id = Id;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
