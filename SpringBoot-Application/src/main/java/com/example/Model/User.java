package com.example.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class User {
    @NotNull
    private String id;
    @NotNull
    @Positive
    private int age;
    @NotNull
    private String name;
    public User(String id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
