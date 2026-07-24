package com.example.Service;
import com.example.Repository.UserRepository;
import com.example.Model.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


@Service
public class UserService implements IService {

    @Autowired private UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public void add(User user) {
        userRepository.add(user);
    }

    public int getUserCount() {
        return userRepository.getAll().size();
    }

}
