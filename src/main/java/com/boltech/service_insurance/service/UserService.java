package com.boltech.service_insurance.service;

import com.boltech.service_insurance.model.User;
import com.boltech.service_insurance.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getById(String id) {
        return userRepository.getById(id);
    }

}
