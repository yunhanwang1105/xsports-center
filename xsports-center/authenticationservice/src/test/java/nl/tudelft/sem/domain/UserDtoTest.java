package nl.tudelft.sem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;

public class UserDtoTest {

    private final String username = "name";
    private final String password = "password";
    private final UserDto userDto = new UserDto(username, password);

    @Test
    public void getUsernameTestEqual() {
        assertEquals(username, userDto.getUsername());
    }

    @Test
    public void nullUsernameTest() {
        UserDto userDtoNull = new UserDto(null, password);
        assertNotNull(userDtoNull.getUsername());
    }

    @Test
    public void nullPasswordTest() {
        assertThrows(BadCredentialsException.class, () -> new UserDto(username, null));
    }

    @Test
    public void getUsernameTestNotEqual() {
        assertNotEquals("notEqual", userDto.getUsername());
    }

    @Test
    public void getPasswordTestEqual() {
        assertEquals(password, userDto.getPassword());
    }

    @Test
    public void getPasswordTestNotEqual() {
        assertNotEquals("notEqual", userDto.getPassword());
    }

    @Test
    public void setUsernameTest() {
        userDto.setUsername("newUsername");
        assertEquals("newUsername", userDto.getUsername());
    }

    @Test
    public void setPasswordTest() {
        userDto.setPassword("newPassword");
        assertEquals("newPassword", userDto.getPassword());
    }

    @Test
    public void hashCodeTest() {
        assertEquals(username.hashCode(), userDto.hashCode());
    }

    @Test
    public void equalsTestSameObject() {
        assertEquals(userDto, userDto);
    }

    @Test
    public void equalsTestDifferentClass() {
        assertNotEquals(userDto, new Object());
    }

    @Test
    public void equalsTestDifferentValues() {
        UserDto thirdUser = new UserDto("newUser", password);
        assertNotEquals(userDto, thirdUser);
    }

    @Test
    public void equalsTestSameValues() {
        UserDto secondUser = new UserDto(username, "newPass");
        assertEquals(userDto, secondUser);
    }

    @Test
    public void toStringTest() {
        String s = "UserDto{username='" + username + '\''
            + ", password='" + password + '\''
            + "}";
        assertEquals(s, userDto.toString());
    }

}
