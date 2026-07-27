package com.example.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import com.example.model.User;

@Repository
public class UserRepository implements IRepository<User> {

    private List<User> users = new ArrayList<>();

    public void add(User user) {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        }
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
