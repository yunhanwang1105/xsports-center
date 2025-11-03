package nl.tudelft.sem.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

@DataJpaTest
public class UserRepositoryTest {

    private final String username = "name";
    private final String password = "password";
    private final UserDto userDto = new UserDto(username, password);

    @Autowired
    private UserRepository userRepository;

    @Test
    public void getUserNullTest() {
        assertEquals(Optional.empty(), userRepository.findById(username));
    }

    @Test
    public void getUserPasswordTest() {
        userRepository.save(userDto);
        UserDto userDto = userRepository.getById(username);
        assertTrue(BCrypt.checkpw(password, userDto.getPassword()));
    }

    @Test
    public void getDifferentUserPasswordTest() {
        userRepository.save(userDto);
        UserDto userDto = userRepository.getById(username);
        assertFalse(BCrypt.checkpw("wrong", userDto.getPassword()));
    }

    @Test
    public void saveUserTest() {
        assertEquals(userDto, userRepository.save(userDto));
        assertNotNull(userRepository.findAll());
    }

    @Test
    public void getUserTest() {
        userRepository.save(userDto);
        assertEquals(Optional.of(userDto), userRepository.findById(username));
    }

    @Test
    public void saveEqualUsers() {
        userRepository.save(userDto);
        UserDto secondUser = new UserDto(username, "newPassword");
        userRepository.save(secondUser);
        assertThat(userRepository.findAll()).hasSize(1).contains(userDto);
    }

    @Test
    public void saveNonEqualUsers() {
        userRepository.save(userDto);
        UserDto thirdUser = new UserDto("newUser", password);
        userRepository.save(thirdUser);
        assertThat(userRepository.findAll()).hasSize(2).contains(userDto, thirdUser);
    }

}
