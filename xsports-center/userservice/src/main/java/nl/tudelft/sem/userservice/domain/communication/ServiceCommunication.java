package nl.tudelft.sem.userservice.domain.communication;

import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.userservice.domain.UserDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class ServiceCommunication {

    /**
     * Add user in authentication service.
     *
     * @param user the user
     * @return the boolean
     */
    public static boolean addAuthentication(UserDto user) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<UserDto> entity = new HttpEntity<>(user, new HttpHeaders());
            ResponseEntity<Void> createResponse = restTemplate
                .exchange("http://localhost:9191/authentication/create", HttpMethod.POST, entity,
                    Void.class);

            return createResponse.getStatusCode().equals(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Authentication service cannot be reached");
        }

        return false;
    }

    /**
     * Check if a user is authenticated.
     *
     * @param user the user
     * @return the boolean
     */
    public static boolean isAuthenticated(UserDto user) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<UserDto> entity = new HttpEntity<>(user, new HttpHeaders());
            ResponseEntity<Void> loginResponse = restTemplate
                .exchange("http://localhost:9191/authentication/login", HttpMethod.POST, entity,
                    Void.class);

            if (loginResponse != null) {
                return loginResponse.getStatusCode().equals(HttpStatus.ACCEPTED);
            }
        } catch (Exception e) {
            log.error("Authentication service cannot be reached");
        }

        return false;
    }
}
