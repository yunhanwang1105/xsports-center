package nl.tudelft.sem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtAuthResponseTest {

    private JwtAuthResponse jwtAuthResponse;

    @BeforeEach
    void setUp() {
        jwtAuthResponse = new JwtAuthResponse("jwtToken");
    }

    @Test
    void getJwt() {
        assertEquals("jwtToken", jwtAuthResponse.getJwt());
    }
}