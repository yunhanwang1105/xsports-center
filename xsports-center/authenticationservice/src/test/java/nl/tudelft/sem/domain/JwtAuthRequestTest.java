package nl.tudelft.sem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtAuthRequestTest {
    private JwtAuthRequest jwtAuthRequest;

    @BeforeEach
    void setUp() {
        jwtAuthRequest = new JwtAuthRequest("username", "password");
    }

    @Test
    void getUserName() {
        assertEquals("username", jwtAuthRequest.getUsername());
    }

    @Test
    void setUserName() {
        jwtAuthRequest.setUsername("username1");
        assertEquals("username1", jwtAuthRequest.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals("password", jwtAuthRequest.getPassword());
    }

    @Test
    void setPassword() {
        jwtAuthRequest.setPassword("password1");
        assertEquals("password1", jwtAuthRequest.getPassword());
    }
}