package com.lria62.QuestionForum.Service;

import com.lria62.QuestionForum.Model.User;
import com.lria62.QuestionForum.Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

}

