package nl.tudelft.sem.facilityservice.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CredentialsTest {

    public String username;
    public String password;
    int role;
    Credentials cred;

    @BeforeEach
    void setUp() {
        username = "JJ";
        password = "Secret";
        role = 1;
        cred = new Credentials(username, password, role);
    }

    @Test
    void constructWithoutRole() {
        Credentials testCred = new Credentials("User", "Pass");
        assertEquals("User", testCred.getUsername());
        assertEquals("Pass", testCred.getPassword());
        assertEquals(-1, testCred.getRole());
    }

    @Test
    void constructWithoutParams() {
        Credentials testCred = new Credentials();
        assertEquals(null, testCred.getUsername());
        assertEquals(null, testCred.getPassword());
        assertEquals(0, testCred.getRole());
    }

    @Test
    void getUsername() {
        assertEquals(username, cred.getUsername());
    }

    @Test
    void setUsername() {
        cred.setUsername("D");
        assertEquals("D", cred.getUsername());
    }

    @Test
    void getPassword() {
        assertEquals(password, cred.getPassword());
    }

    @Test
    void setPassword() {
        cred.setPassword("pass");
        assertEquals("pass", cred.getPassword());
    }

    @Test
    void getRole() {
        assertEquals(role, cred.getRole());
    }

    @Test
    void setRole() {
        cred.setRole(0);
        assertEquals(0, cred.getRole());
    }
}