package nl.tudelft.sem.reservationservice.communication;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.reservationservice.vo.TeamDto;
import nl.tudelft.sem.reservationservice.vo.UserDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class ServiceCommunication {

    /**
     * Checks if the user is authenticated by sending request to authenticationservice.
     *
     * @param username The username
     * @param password The password
     * @return True iff authenticated
     */
    public static boolean isAuthenticated(String username, String password) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<Credentials> entity =
                new HttpEntity<>(new Credentials(username, password), new HttpHeaders());
            ResponseEntity<Void> loginResponse =
                restTemplate.exchange("http://localhost:9191/authentication/login", HttpMethod.POST,
                    entity, Void.class);

            return loginResponse.getStatusCode().equals(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("Authentication service cannot be reached");
        }

        return false;
    }

    /**
     * Checks if the user is indeed a member of the team.
     *
     * @param username The username
     * @param teamId   The team id
     * @return True iff the user is in the team
     */
    public static boolean isInTeam(String username, Long teamId) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
            TeamDto userResponse =
                restTemplate.exchange("http://localhost:9191/teams/" + teamId, HttpMethod.GET,
                    entity, TeamDto.class).getBody();

            if (userResponse != null) {
                Set<String> usernames = userResponse.getUsers().stream().map(UserDto::getUsername)
                    .collect(Collectors.toSet());
                return usernames.contains(username);
            }
        } catch (Exception e) {
            log.error("Team service cannot be reached");
        }

        return false;
    }
}
