package com.example;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import com.example.Model.User;
import com.example.Repository.UserRepository;

public class UserRepositoryLayerTest {
    private UserRepository userRepository;

    @Test
    void saveAll_success() {
    List<User> customers = Arrays.asList(
            new User("1", 25, "John Doe"),
            new User("2", 30, "Jane Doe"),
            new User("3", 22, "Alice Smith")
    );
    Iterable<User> allCustomer = userRepository.saveAll(customers);

    AtomicInteger validIdFound = new AtomicInteger();
    allCustomer.forEach(customer -> {
        if(customer.getId()!= null && !customer.getId().isEmpty()) {
            validIdFound.getAndIncrement();
        }
    });

    assert(validIdFound.intValue() == 3);
}

    @Test
    void findAll_success() {
        List<User> allCustomer = userRepository.getAll();
        assert(allCustomer.size() >0);
}

    @Test
    void add_success() {
        User user = new User("4", 28, "Bob Johnson");
        userRepository.add(user);
        List<User> allCustomer = userRepository.getAll();
        assert(allCustomer.contains(user));
    
}
    @Test 
    void try_validate_user() {
        User user = new User(null, -5, null);
        try {
            userRepository.add(user);
        } catch (Exception e) {
            assert(e instanceof jakarta.validation.ConstraintViolationException);
    }

}
}