package nl.tudelft.sem.application.securityconfigtests;

import java.util.List;
import nl.tudelft.sem.domain.UserDto;
import nl.tudelft.sem.domain.UserDtoDetails;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class AuthTestConfig {

    /**
     * Creates a dummy user to be used in authentication testing environments.
     *
     * @return the user
     */
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserDto user = new UserDto("username", "password");
        UserDtoDetails userDtoDetails = new UserDtoDetails(user);
        return new InMemoryUserDetailsManager(List.of(
            userDtoDetails
        ));
    }
}