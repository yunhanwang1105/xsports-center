package nl.tudelft.sem.lessonservice.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CredentialsTest {

    String username;
    String password;
    int role;
    Credentials cred;

    @BeforeEach
    void setup() {
        username = "user";
        password = "pass";
        role = 1;
        cred = new Credentials(username, password, role);
    }

//    @Test
//    void getUsername() {
//        assertEquals(username, cred.getUsername());
//    }

//    @Test
//    void setUsername() {
//        cred.setUsername("New");
//        assertEquals("New", cred.getUsername());
//    }

//    @Test
//    void getPassword() {
//        assertEquals(password, cred.getPassword());
//    }

//    @Test
//    void setPassword() {
//        cred.setPassword("pass2");
//        assertEquals("pass2", cred.getPassword());
//    }

    @Test
    void getRole() {
        assertEquals(role, cred.getRole());
    }

//    @Test
//    void setRole() {
//        cred.setRole(0);
//        assertEquals(0, cred.getRole());
//    }
}