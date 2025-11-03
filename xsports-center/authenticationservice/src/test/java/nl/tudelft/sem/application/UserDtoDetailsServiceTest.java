package nl.tudelft.sem.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import nl.tudelft.sem.domain.UserDto;
import nl.tudelft.sem.domain.UserDtoDetails;
import nl.tudelft.sem.domain.UserRepositoryAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

public class UserDtoDetailsServiceTest {

    private final String username = "name";
    private final String password = "password";
    private final UserDto userDto = new UserDto(username, password);
    private final UserDtoDetails userDtoDetails = new UserDtoDetails(userDto);

    @Mock
    UserRepositoryAccessor userRepositoryAccessor;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    private final UserDtoDetailsService userDtoDetailsService = new UserDtoDetailsService();

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserTest() {
        when(userRepositoryAccessor.loadByUsername(username)).thenReturn(Optional.of(userDto));
        assertEquals(userDtoDetails, userDtoDetailsService.loadUserByUsername(username));
    }

    @Test
    public void loadNullUserTest() {
        when(userRepositoryAccessor.loadByUsername(username)).thenReturn(Optional.empty());
        Exception exception = assertThrows(UsernameNotFoundException.class,
            () -> userDtoDetailsService.loadUserByUsername(username));
        assertEquals(exception.getMessage(), "Not found: " + username);
    }
}
