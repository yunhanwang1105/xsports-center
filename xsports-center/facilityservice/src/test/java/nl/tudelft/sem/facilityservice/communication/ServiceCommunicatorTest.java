package nl.tudelft.sem.facilityservice.communication;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

class ServiceCommunicatorTest {
    @Mock
    RestTemplate restTemplate;
    @Mock
    RestTemplate restTemplate2;
    private ServiceCommunicator serviceCommunicator;
    private ServiceCommunicator serviceCommunicator2;

    @BeforeEach
    void constructor() {
        restTemplate = Mockito.mock(RestTemplate.class);
        when(restTemplate.exchange(eq("http://localhost:9191/users/Admin"), any(), any(),
            eq(Credentials.class))).thenReturn(
            new ResponseEntity<Credentials>(
                new Credentials("Admin", "", 0), HttpStatus.OK));
        when(restTemplate.exchange(eq("http://localhost:9191/users/Hans"), any(), any(),
            eq(Credentials.class))).thenReturn(
            new ResponseEntity<Credentials>(
                new Credentials("Hans", "", 1), HttpStatus.OK));

        when(restTemplate.exchange(eq("http://localhost:9191/authentication/login"), any(), argThat(
            (HttpEntity<Credentials> e) -> (e.getBody().username.equals("Hans")
                && e.getBody().password.equals("@nd3rs"))), eq(Void.class))).thenReturn(
            new ResponseEntity<Void>(HttpStatus.ACCEPTED));
        when(restTemplate.exchange(eq("http://localhost:9191/authentication/login"), any(), argThat(
            (HttpEntity<Credentials> e) -> (e.getBody().username.equals("Hans")
                && e.getBody().password.equals("WrongPassword"))), eq(Void.class))).thenReturn(
            new ResponseEntity<Void>(HttpStatus.FORBIDDEN));

        restTemplate2 = Mockito.mock(RestTemplate.class);
        when(restTemplate2.exchange(any(), any(), any(), eq(Credentials.class))).thenThrow(
            new ResourceAccessException("Read timed out"));
        when(restTemplate2.exchange(any(), any(), any(), eq(Void.class))).thenThrow(
            new ResourceAccessException("Read timed out"));

        serviceCommunicator = new ServiceCommunicator(restTemplate);
        serviceCommunicator2 = new ServiceCommunicator(restTemplate2);
    }

    @Test
    void emptyConstructor() {
        assertNotNull(new ServiceCommunicator());
    }

    @Test
    void isAuthenticatedTrue() {
        assertTrue(serviceCommunicator
            .isAuthenticated("Hans", "@nd3rs"));
    }

    @Test
    void isAuthenticatedFalse() {
        assertFalse(serviceCommunicator
            .isAuthenticated("Hans", "WrongPassword"));
    }

    @Test
    void isAuthenticatedServerDown() {
        assertFalse(serviceCommunicator2
            .isAuthenticated("Hans", "@nd3rs"));
    }

    @Test
    void isAdminTrue() {
        assertTrue(serviceCommunicator.isAdmin("Admin"));
    }

    @Test
    void isAdminFalse() {
        assertFalse(serviceCommunicator.isAdmin("Hans"));
    }

    @Test
    void isAdminServerDown() {
        assertFalse(serviceCommunicator2.isAdmin("Admin"));
    }
}