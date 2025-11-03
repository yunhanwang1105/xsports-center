package nl.tudelft.sem.facilityservice.communication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ServiceCommunicator {
    RestTemplate restTemplate;

    public ServiceCommunicator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ServiceCommunicator() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Check if a user can be authenticated using the provided password.
     *
     * @param username The username of the user
     * @param password The password of the user
     * @return Whether the user is authenticated
     */
    public boolean isAuthenticated(String username, String password) {
        try {
            HttpEntity<Credentials>
                entity = new HttpEntity<>(new Credentials(username, password), new HttpHeaders());
            ResponseEntity<Void> loginResponse =
                restTemplate.exchange("http://localhost:9191/authentication/login",
                    HttpMethod.POST, entity, Void.class);

            if (loginResponse != null) {
                return loginResponse.getStatusCode().equals(HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            log.error("Authentication service cannot be reached");
        }

        return false;
    }

    /**
     * Check if a user is admin.
     *
     * @param username The user to check
     * @return Whether the user is admin
     */
    public boolean isAdmin(String username) {
        try {
            HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
            Credentials userResponse = restTemplate.exchange("http://localhost:9191/users/" + username, HttpMethod.GET, entity, Credentials.class).getBody();

            if (userResponse != null) {
                return userResponse.getRole() == 0;
            }
        } catch (Exception e) {
            log.error("User service cannot be reached");
        }

        return false;
    }
}
