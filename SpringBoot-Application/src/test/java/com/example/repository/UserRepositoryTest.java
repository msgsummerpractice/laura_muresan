package com.example.repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static java.util.stream.StreamSupport.stream;
import java.util.Arrays;
import java.util.List;
import com.example.model.User;
import com.example.repository.UserRepository;

public class UserRepositoryTest {
    private UserRepository userRepository;
    List<User> customers = Arrays.asList(
            new User("1", 25, "John Doe"),
            new User("2", 30, "Jane Doe"),
            new User("3", 22, "Alice Smith")
    );

    @Test
    void saveAll_returnsEmptyList_whenNoValidUsersAdded() {
    
    List<User> allCustomer = userRepository.saveAll(customers);

    boolean allSaved = stream(allCustomer.spliterator(), false)
            .allMatch(customer -> customer.getId() != null && customer.getAge() > 0 && customer.getName() != null);

    assert(allSaved);
}

    @Test
    void getAll_returnsZero_whenNoUsersAdded() {
        List<User> allCustomer = userRepository.getAll();
        assert(allCustomer.isEmpty());
}

    @Test
    void add_addsUser_whenUserIsValid() {
        User user = new User("4", 28, "Bob Johnson");
        userRepository.add(user);
        List<User> allCustomer = userRepository.getAll();
        assert(allCustomer.contains(user));
    
}
    @Test 
    void add_throwsException_whenUserIsInvalid() {
        User user = new User(null, -5, null);
        assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            userRepository.add(user);
        });
    }

}
