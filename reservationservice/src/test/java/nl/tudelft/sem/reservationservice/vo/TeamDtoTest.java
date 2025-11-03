package nl.tudelft.sem.reservationservice.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TeamDtoTest {

    private final Long id = 1L;
    private final String name = "Raimon";
    private final String joinToken = "Endou123";
    private final String creatorId = "Mark";
    private final Set<UserDto> users = new HashSet<>();
    private final TeamDto teamDto = new TeamDto(id, name, joinToken, creatorId, users);
    private final String password = "123";
    private final Set<String> teamIds = new HashSet<>();

    // Test get team id.
    @Test
    void getId() {
        assertEquals(id, teamDto.getId());
        assertNotEquals(2L, teamDto.getId());
    }

    // Test set team id.
    @Test
    void setId() {
        teamDto.setId(2L);
        assertEquals(2L, teamDto.getId());
    }

    // Test get team name.
    @Test
    void getName() {
        assertEquals("Raimon", teamDto.getName());
        assertNotEquals("Royal Academy", teamDto.getName());
    }

    // Test set team name.
    @Test
    void setName() {
        teamDto.setName("Chrono Storm");
        assertEquals("Chrono Storm", teamDto.getName());
    }

    // Test get team's join token.
    @Test
    void getJoinToken() {
        assertEquals("Endou123", teamDto.getJoinToken());
        assertNotEquals("Evans123", teamDto.getJoinToken());
    }

    // Test set team's join token.
    @Test
    void setJoinToken() {
        teamDto.setJoinToken("Evans123");
        assertEquals("Evans123", teamDto.getJoinToken());
    }

    // Test get team's creator id.
    @Test
    void getCreatorId() {
        assertEquals("Mark", teamDto.getCreatorId());
        assertNotEquals(2L, teamDto.getId());
    }

    // Test set team's creator id.
    @Test
    void setCreatorId() {
        teamDto.setCreatorId("Todd");
        assertEquals("Todd", teamDto.getCreatorId());
    }

    // Test get team's member.
    @Test
    void getUsers() {
        assertEquals(users, teamDto.getUsers());
        final String username = "Shawn";
        final int role = 1;
        final UserDto userDto = new UserDto(username, password, role, null);
        Set<UserDto> users2 = new HashSet<>();
        users2.add(userDto);
        assertNotEquals(users2, teamDto.getUsers());
    }

    // Test set team's member.
    @Test
    void setUsers() {
        Set<UserDto> users2 = new HashSet<>();
        assertEquals(users, teamDto.getUsers());
        final String username = "Shawn";
        final int role = 1;
        final UserDto userDto = new UserDto(username, password, role, teamIds);
        users2.add(userDto);
        teamDto.setUsers(users2);
        assertEquals(users2, teamDto.getUsers());
    }

    // Test equals 1.
    @Test
    void testSameObjectEquals() {
        assertEquals(teamDto, teamDto);
    }

    // Test equals 2.
    @Test
    void testEqualsNull() {
        assertNotEquals(null, teamDto);
    }

    // Test equals 3.
    @Test
    void testEqualsTrue() {
        TeamDto teamDto2 = new TeamDto(id, name, joinToken, creatorId, users);
        assertEquals(teamDto, teamDto2);
    }

    // Test equals when id differs.
    @Test
    void testEqualsIdNotSame() {
        TeamDto teamDto2 = new TeamDto(10L, "Royal Academy", "Kidou123", "Jude", users);
        assertNotEquals(teamDto, teamDto2);
    }

    // Test equals when name differs.
    @Test
    void testEqualsNameNotSame() {
        TeamDto teamDto2 = new TeamDto(3L, null, "Kidou123", "Jude", users);
        assertEquals(teamDto2, teamDto2);
        assertNotEquals(teamDto, teamDto2);
        assertNotEquals(teamDto2, teamDto);
        assertFalse(teamDto.equals(null));
        assertFalse(teamDto.equals("s"));

        TeamDto teamDto3 = new TeamDto(2L, "Royal Academy", "Kidou123", "Jude", users);
        assertNotEquals(teamDto, teamDto3);
        assertNotEquals(teamDto3, teamDto);
    }

    // Test hashCode.
    @Test
    void testHashCode() {
        TeamDto teamDto2 = new TeamDto(id, name, joinToken, creatorId, users);
        assertTrue(teamDto.equals(teamDto2) && teamDto2.equals(teamDto));
        assertEquals(teamDto.hashCode(), teamDto2.hashCode());
    }

    // Test toString.
    @Test
    void testToString() {
        String toString = teamDto.toString();
        assertEquals(toString, teamDto.toString());
    }

}