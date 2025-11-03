package nl.tudelft.sem.userservice.domain;

import static nl.tudelft.sem.userservice.domain.utilities.UserUtility.mapUserToUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class UserUtilityTest {
    // for userDto
    private final String username = "name";
    private final int role = 0;
    private final String password = "abc";
    private final UserDto userDto = new UserDto(username, role);
    private final UserDto userDtoWithPassword = new UserDto(username, password);
    private final User userSetNull = new User(username, role, null);
    // for user
    private final Set<Team> set = new HashSet<>();
    private final User user = new User(username, role, set);

    @Test
    public void mapUserToUserDtoTest() {
        assertEquals(userDto, mapUserToUserDto(user));
    }

    @Test
    public void mapUserToUserDtoTestButSetNull() {
        assertEquals(userDto, mapUserToUserDto(userSetNull));
    }

}
