package nl.tudelft.sem.lessonservice.communication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * The type Service communication.
 */
@Slf4j
public class ServiceCommunication {
    /**
     * Checks if authenticated.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     */
    public static boolean isAuthenticated(String username, String password) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Credentials> entity =
                new HttpEntity<>(new Credentials(username, password), new HttpHeaders());
            ResponseEntity<Void> loginResponse =
                restTemplate.exchange("http://localhost:9191/authentication/login", HttpMethod.POST,
                    entity, Void.class);

            if (loginResponse != null) {
                return loginResponse.getStatusCode().equals(HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            log.error("Authentication service cannot be reached");
        }

        return false;
    }

    /**
     * Checks if admin.
     *
     * @param username the username
     * @return the boolean
     */
    public static boolean isAdmin(String username) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
            Credentials userResponse =
                restTemplate.exchange("http://localhost:9191/users/" + username, HttpMethod.GET,
                    entity, Credentials.class).getBody();

            if (userResponse != null) {
                return userResponse.getRole() == 0;
            }
        } catch (Exception e) {
            log.error("User service cannot be reached");
        }

        return false;
    }
}
