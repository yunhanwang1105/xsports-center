package nl.tudelft.sem.userservice.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    private final String username = "name";
    private final int role = 0;
    private Set<Team> set;
    private final User user = new User(username, role, set);

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserNullTest() {
        assertEquals(Optional.empty(), userRepository.findById(username));
    }

    @Test
    public void saveUserTest() {
        assertEquals(user, userRepository.save(user));
        assertNotNull(userRepository.findAll());
    }

    @Test
    public void getUserTest() {
        userRepository.save(user);
        assertEquals(Optional.of(user), userRepository.findById(username));
    }

    @Test
    public void saveEqualUsers() {
        userRepository.save(user);
        User secondUser = new User(username, role, set);
        userRepository.save(secondUser);
        assertThat(userRepository.findAll()).hasSize(1).contains(user);
    }

    @Test
    public void saveNonEqualUsers() {
        userRepository.save(user);
        User thirdUser = new User("newUser", role, set);
        userRepository.save(thirdUser);
        assertThat(userRepository.findAll()).hasSize(2).contains(user, thirdUser);
    }

}
