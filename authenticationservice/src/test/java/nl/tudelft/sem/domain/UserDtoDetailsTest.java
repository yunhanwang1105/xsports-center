package nl.tudelft.sem.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDtoDetailsTest {

    private final String username = "name";
    private final String password = "password";
    private final UserDto userDto = new UserDto(username, password);
    private final UserDtoDetails userDtoDetails = new UserDtoDetails(userDto);

    @Test
    public void getUsernameTestEqual() {
        assertEquals(username, userDtoDetails.getUsername());
    }

    @Test
    public void getUsernameTestNotEqual() {
        assertNotEquals("notEqual", userDtoDetails.getUsername());
    }

    @Test
    public void getPasswordTestEqual() {
        assertEquals(password, userDtoDetails.getPassword());
    }

    @Test
    public void getPasswordTestNotEqual() {
        assertNotEquals("notEqual", userDtoDetails.getPassword());
    }

    @Test
    public void equalsSameObjectTest() {
        assertEquals(userDtoDetails, userDtoDetails);
    }

    @Test
    public void equalsDifferentClassTest() {
        assertNotEquals(userDtoDetails, new Object());
    }

    @Test
    public void equalsSameValuesTest() {
        assertEquals(userDto, userDtoDetails.getUser());
    }

    @Test
    public void equalsDifferentValuesTest() {
        assertNotEquals(new UserDto("notEqual", password), userDtoDetails.getUser());
    }

    @Test
    public void hashCodeTest() {
        assertEquals(username.hashCode(), userDtoDetails.hashCode());
    }

    @Test
    public void getAuthoritiesTest() {
        assertEquals(
            Collections.singletonList(new SimpleGrantedAuthority("basic_role")),
            userDtoDetails.getAuthorities());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(userDtoDetails.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(userDtoDetails.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(userDtoDetails.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(userDtoDetails.isEnabled());
    }
}
