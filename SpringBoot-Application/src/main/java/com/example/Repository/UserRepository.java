package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public class UserRepository implements IRepository<User> {

    private List<User> users = new ArrayList<>();

    public void add(User user) {
        users.add(user);
    }

    public List<User> getAll() {
        return users;
    }

    public List<User> saveAll(List<User> customers) {
        users.addAll(customers);
        return customers;
    }

}
