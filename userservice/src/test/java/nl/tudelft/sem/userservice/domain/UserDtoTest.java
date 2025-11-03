package nl.tudelft.sem.userservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class UserDtoTest {

    private final String username = "name";
    private final int role = 0;
    private final String password = "abc";
    private final UserDto userDto = new UserDto(username, role);
    private final UserDto userDtoWithPassword = new UserDto(username, password);

    @Test
    public void getUsernameTest() {
        assertEquals(username, userDto.getUsername());
        assertNotEquals("notEqual", userDto.getUsername());
    }

    @Test
    public void setUsernameTest() {
        userDto.setUsername("newUsername");
        assertEquals("newUsername", userDto.getUsername());
    }

    @Test
    public void getPasswordTest() {
        assertEquals(password, userDtoWithPassword.getPassword());
        assertNotEquals("notEqual", userDtoWithPassword.getPassword());
    }

    @Test
    public void setPasswordTest() {
        userDtoWithPassword.setPassword("def");
        assertEquals("def", userDtoWithPassword.getPassword());
    }

    @Test
    public void getRoleTest() {
        assertEquals(role, userDto.getRole());
        assertNotEquals(role + 1, userDto.getRole());
    }

    @Test
    public void setRoleTest() {
        userDto.setRole(1);
        assertEquals(1, userDto.getRole());
    }

    @Test
    public void getTeamIdsTest() {
        assertEquals(userDto.getTeamIds(), new HashSet<>());
    }

    @Test
    public void setTeamIdsTest() {
        Set<String> teamIds = new HashSet<>();
        teamIds.add("abc");
        userDto.setTeamIds(teamIds);
        assertEquals(teamIds, userDto.getTeamIds());
    }

    @Test
    public void equalsTest() {
        UserDto secondUser = new UserDto(username, 0);
        UserDto thirdUser = new UserDto("newUser", role);

        assertEquals(userDto, secondUser);
        assertNotEquals(userDto, thirdUser);
    }

    @Test
    public void equalsClassDifferent() {
        TeamDto teamDto = new TeamDto();

        assertNotEquals(userDto, teamDto);
    }

    @Test
    public void equalsTestRoleDifferent() {
        UserDto thirdUser = new UserDto(username, 2);

        assertNotEquals(userDto, thirdUser);
    }

    @Test
    public void equalsTestTeam() {
        UserDto thirdUser = new UserDto("user", 2);
        UserDto fourthUser = new UserDto("user", 2);

        assertEquals(fourthUser, thirdUser);

        thirdUser.setTeamIds(new HashSet<>());
        fourthUser.setTeamIds(null);

        assertNotEquals(fourthUser, thirdUser);
    }

    @Test
    public void equalsTestOneTeamIdsNotEmpty() {
        UserDto secondUser = new UserDto(username, role);
        Set<String> teamIds = new HashSet<>();
        teamIds.add("abc");
        secondUser.setTeamIds(teamIds);
        assertNotEquals(userDto, secondUser);
    }

    @Test
    public void equalsTestOneTeamIdsNull() {
        UserDto secondUser = new UserDto(username, role);
        secondUser.setTeamIds(null);
        assertNotEquals(userDto, secondUser);
    }

    @Test
    public void equalsTestSameObject() {
        assertEquals(userDto, userDto);
    }

    @Test
    public void equalsTestNull() {
        assertFalse(userDto.equals(null));
        assertNotEquals(userDto, null);
    }

    @Test
    public void testEquals_Symmetric() {
        UserDto secondUser = new UserDto(username, 0);
        UserDto thirdUser = new UserDto(username, 0);
        assertTrue(secondUser.equals(thirdUser) && thirdUser.equals(secondUser));
        assertTrue(secondUser.hashCode() == thirdUser.hashCode());
    }

    @Test
    public void testEquals_SymmetricTeamIdsNull() {
        UserDto secondUser = new UserDto(username, 0);
        secondUser.setTeamIds(null);
        UserDto thirdUser = new UserDto(username, 0);
        thirdUser.setTeamIds(null);
        assertTrue(secondUser.equals(thirdUser) && thirdUser.equals(secondUser));
        assertTrue(secondUser.hashCode() == thirdUser.hashCode());
    }

    @Test
    public void toStringTest() {
        String toString = userDto.toString();
        assertTrue(toString.contains("UserDto{username='" + username));
    }

}
