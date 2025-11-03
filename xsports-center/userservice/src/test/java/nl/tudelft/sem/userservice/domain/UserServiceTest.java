package nl.tudelft.sem.userservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import nl.tudelft.sem.userservice.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    private final String username = "name";
    private final int role = 0;
    @InjectMocks
    private final UserService userService = new UserService();
    private Set<Team> set;
    private final User user = new User(username, role, set);
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveUserTest() {
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.saveUser(user));
        assertNotNull(userRepository.findAll());
    }

    @Test
    public void findByUsernameTest() {
        when(userRepository.findByUsername(username)).thenReturn(user);
        assertEquals(user, userService.findByUsername(username));
    }

    @Test
    public void findByUsernameNullTest() {
        when(userRepository.findByUsername(username)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> userService.findByUsername(username));
    }

    @Test
    public void updateRole() {
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        final String username = "name";
        final String password = "password";
        final int role = 2;
        User userNew = new User(username, role, set);
        assertEquals(userNew, userService.updateRole(username, 2));
    }

    @Test
    public void updateRoleNull() {
        when(userRepository.findByUsername(username)).thenReturn(null);
        when(userRepository.save(user)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> userService.updateRole(username, 2));
    }

    @Test
    public void getAllAdminsTest() {
        Set<User> set = new HashSet<>();
        set.add(user);
        when(userRepository.findAllByRole(0)).thenReturn(set);
        assertEquals(set, userService.getAllAdmins());
    }

}
