package com.rishi.journalApp.controller;

import com.rishi.journalApp.entity.JournalEntry;
import com.rishi.journalApp.entity.User;
import com.rishi.journalApp.repository.UserRepository;
import com.rishi.journalApp.service.JournalEntryService;
import com.rishi.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    //    special type of classes or components handle HTTP requests

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @GetMapping
//    public ResponseEntity<?> getAllUsers(){ // localhost:8080/journal GET
//        List<User> allUser = userService.getUsers();
//        if(!allUser.isEmpty()){
//            return new ResponseEntity<>(allUser, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    // Update the User
    @PutMapping
    public ResponseEntity<?> updateUserById(@RequestBody User newUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currUser = userService.findByUsername(username);
        currUser.setUsername(!newUser.getUsername().isEmpty() ? newUser.getUsername() : currUser.getUsername());
        currUser.setPassword(!newUser.getPassword().isEmpty() ? newUser.getPassword() : currUser.getPassword());
        userService.saveNewUser(currUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Delete the user
    @DeleteMapping
    public ResponseEntity<?> deleteEntryById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
