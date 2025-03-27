package com.rishi.journalApp.service;

import com.rishi.journalApp.entity.User;
import com.rishi.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public void saveUser(User newUser) {
        userRepository.save(newUser);
    }

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User newUser) {
        try {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            newUser.setRoles(Arrays.asList("USER"));
            userRepository.save(newUser);
        } catch (Exception e) {
            logger.trace("logging the trace");
            logger.debug("logging the debug");
            logger.info("logging the info");
            logger.warn("logging the warn");
            logger.error("logging the error");
//            logger.error("logging the error {} {}", newUser.getUsername(), e.getMessage());
        }

    }

    public void saveAdmin(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepository.save(newUser);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId myId) {
        return userRepository.findById(myId);
    }

    public void deleteUser(ObjectId myId) {
        userRepository.deleteById(myId);
    }

    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }
}

// controller --> service --> repository