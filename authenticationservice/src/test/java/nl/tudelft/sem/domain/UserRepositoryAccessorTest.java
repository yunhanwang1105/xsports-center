package nl.tudelft.sem.domain;

import nl.tudelft.sem.application.UserDtoDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserRepositoryAccessorTest {

    private final String username = "name";
    private final String password = "password";
    private final UserDto userDto = new UserDto(username, password);

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private final UserRepositoryAccessor userRepositoryAccessor = new UserRepositoryAccessor();

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadByUsernameSuccessful() {
        when(userRepository.findById(username)).thenReturn(Optional.of(userDto));
        assertEquals(Optional.of(userDto), userRepositoryAccessor.loadByUsername(username));
    }

    @Test
    void loadByUsernameFailed() {
        when(userRepository.findById(username)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), userRepositoryAccessor.loadByUsername(username));
    }

    @Test
    void loadByUserSuccessful() {
        when(userRepository.findById(username)).thenReturn(Optional.of(userDto));
        assertEquals(Optional.of(userDto), userRepositoryAccessor.loadByUser(userDto));
    }

    @Test
    void loadByUserFailed() {
        when(userRepository.findById(username)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), userRepositoryAccessor.loadByUser(userDto));
    }

    @Test
    void saveUserSuccessful() {
        when(userRepository.findById(username)).thenReturn(Optional.empty());
        assertTrue(userRepositoryAccessor.saveUser(userDto));
    }

    @Test
    void saveUserFailed() {
        when(userRepository.findById(username)).thenReturn(Optional.of(userDto));
        assertFalse(userRepositoryAccessor.saveUser(userDto));
    }

}
