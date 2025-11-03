package nl.tudelft.sem.reservationservice.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UserDtoTest {

    private final String username = "name";
    private final int role = 0;
    private final String password = "123";
    private final Set<String> teamIds = new HashSet<>();
    private final UserDto userDto = new UserDto(username, password, role, teamIds);

    // Test get username.
    @Test
    public void getUsernameTest() {
        assertEquals(username, userDto.getUsername());
        assertNotEquals("notEqual", userDto.getUsername());
    }

    // Test get user's role.
    @Test
    public void getRoleTest() {
        assertEquals(role, userDto.getRole());
        assertNotEquals(role + 1, userDto.getRole());
    }

    // Test set username.
    @Test
    public void setUsernameTest() {
        userDto.setUsername("newUsername");
        assertEquals("newUsername", userDto.getUsername());
    }

    // Test set user's role.
    @Test
    public void setRoleTest() {
        userDto.setRole(1);
        assertEquals(1, userDto.getRole());
    }

    // Test get user's team ids.
    @Test
    public void getTeamIdsTest() {
        assertEquals(userDto.getTeamIds(), teamIds);
    }

    // Test set user's team ids.
    @Test
    public void setTeamIdsTest() {
        Set<String> teamIds = new HashSet<>();
        teamIds.add("abc");
        userDto.setTeamIds(teamIds);
        assertEquals(teamIds, userDto.getTeamIds());
    }

    // Test equals.
    @Test
    public void equalsTest() {
        UserDto secondUser = new UserDto(username, password, 0, teamIds);
        UserDto thirdUser = new UserDto("newUser", password, role, teamIds);

        assertEquals(userDto, secondUser);
        assertNotEquals(userDto, thirdUser);
    }

    // Test equals.
    @Test
    public void equalsTestOneTeamIdsNull() {
        UserDto secondUser = new UserDto("han", password, role, teamIds);
        secondUser.setTeamIds(null);
        assertNotEquals(userDto, secondUser);
        assertFalse(userDto.equals("s"));
        assertFalse(userDto.equals(null));

    }

    // Test equals.
    @Test
    public void equalsTestSameObject() {
        assertEquals(userDto, userDto);
    }

    // Test equals.
    @Test
    public void equalsTestNull() {
        assertNotEquals(null, userDto);
        assertNotEquals(userDto, null);
    }

    // Test equals.
    @Test
    public void testEqualsSymmetric() {
        UserDto secondUser = new UserDto(username, password, 0, teamIds);
        UserDto thirdUser = new UserDto(username, password, 0, teamIds);
        assertTrue(secondUser.equals(thirdUser) && thirdUser.equals(secondUser));
        assertEquals(secondUser.hashCode(), thirdUser.hashCode());
    }

    // Test equals.
    @Test
    public void testEqualsSymmetricTeamIdsNull() {
        UserDto secondUser = new UserDto(username, password, 0, teamIds);
        UserDto thirdUser = new UserDto(username, password, 0, teamIds);
        assertTrue(secondUser.equals(thirdUser) && thirdUser.equals(secondUser));
        assertEquals(secondUser.hashCode(), thirdUser.hashCode());
    }

    // Test toString.
    @Test
    public void toStringTest() {
        String toString = userDto.toString();
        assertEquals(toString, userDto.toString());
    }

    // Test get user's password.
    @Test
    void getPassword() {
        assertEquals(password, userDto.getPassword());
    }

    // Test set user's password.
    @Test
    void setPassword() {
        userDto.setPassword("12");
        assertEquals("12", userDto.getPassword());
    }

}