package com.rishi.journalApp.service;

import com.rishi.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testAdd(){
        assertEquals(4, 2 + 2);
    }

    @ParameterizedTest
    @CsvSource({
            "4, 2, 2",
            "5, 3, 2",
            "2, 3, 1"
    })
    public void testAddUsingParams(int expected, int a, int b){
        assertEquals(expected, a + b);
    }

    @Test
    public void testFindByUsername(){
        assertNotNull(userRepository.findByUsername("rishi"));
    }

    @ParameterizedTest
    @CsvSource({
            "rishi",
            "suman",
            "anjali",
            "rishu"
    })
    public void testFindByUsername(String username){
        assertNotNull(userRepository.findByUsername(username));
    }
}
