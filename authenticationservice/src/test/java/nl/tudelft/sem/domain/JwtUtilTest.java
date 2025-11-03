package nl.tudelft.sem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

class JwtUtilTest {

    private UserDetails userDetails = new UserDtoDetails(new UserDto("Username", "Password"));
    private String token;
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        token = jwtUtil.generateToken(userDetails);
    }

    @Test
    void extractUserName() {
        assertEquals("Username", jwtUtil.extractUsername(token));
    }

    @Test
    void extractExpiration() {
        assertEquals(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10).getDate(),
            jwtUtil.extractExpiration(token).getDate());
    }

    @Test
    void extractClaim() {
    }

    @Test
    void createToken() {
    }

    @Test
    void validateToken() {
        UserDetails userDetails2 = new UserDtoDetails(new UserDto("Other", "Something"));
        String token2 = jwtUtil.generateToken(userDetails2);
        assertTrue(jwtUtil.validateToken(token, userDetails));
        assertFalse(jwtUtil.validateToken(token2, userDetails));
    }
}