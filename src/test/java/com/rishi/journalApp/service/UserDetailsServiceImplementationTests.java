package com.rishi.journalApp.service;

import com.rishi.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;

//@SpringBootTest -> it still loads whole component we need to eliminate this
public class UserDetailsServiceImplementationTests {
//    @Autowired
    @InjectMocks
    private UserDetailsServiceImplementation userDetailsService;

    // UserDetailsServiceImplementation (Autowired) -> UserRepository (Mock) it -> we use @MockBean
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest() {
        com.rishi.journalApp.entity.User mockUser = new com.rishi.journalApp.entity.User();
        mockUser.setUsername("rishi");
        mockUser.setPassword("kuchvidedo");
        mockUser.setRoles(new ArrayList<>());

        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(mockUser);
        UserDetails user = userDetailsService.loadUserByUsername("rishi");

        Assertions.assertNotNull(user);
    }
}
