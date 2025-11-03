package nl.tudelft.sem.lessonservice.communication;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class ServiceCommunicationTest {

    @Test
    void isNotAuthenticated() {
        assertFalse(ServiceCommunication.isAuthenticated("Jos", "pass"));
    }

    @Test
    void isAuthenticated() {

    }

    @Test
    void isAdmin() {
    }
}