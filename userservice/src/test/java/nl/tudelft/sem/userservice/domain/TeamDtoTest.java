package nl.tudelft.sem.userservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class TeamDtoTest {

    private final Long id = 1L;
    private final String name = "Raimon";
    private final String joinToken = "Endou123";
    private final String creatorId = "Mark";
    private final TeamDto teamDto = new TeamDto(id, name, joinToken, creatorId);
    private final Set<UserDto> users = new HashSet<>();

    @Test
    void emptyConstructor() {
        TeamDto teamDto = new TeamDto();
        teamDto.setId(2L);
        assertEquals(2L, teamDto.getId());
    }

    @Test
    void getId() {
        assertEquals(id, teamDto.getId());
        assertNotEquals(2L, teamDto.getId());
    }

    @Test
    void setId() {
        teamDto.setId(2L);
        assertEquals(2L, teamDto.getId());
    }

    @Test
    void getName() {
        assertEquals("Raimon", teamDto.getName());
        assertNotEquals("Royal Academy", teamDto.getName());
    }

    @Test
    void setName() {
        teamDto.setName("Chrono Storm");
        assertEquals("Chrono Storm", teamDto.getName());
    }

    @Test
    void getJoinToken() {
        assertEquals("Endou123", teamDto.getJoinToken());
        assertNotEquals("Evans123", teamDto.getJoinToken());
    }

    @Test
    void setJoinToken() {
        teamDto.setJoinToken("Evans123");
        assertEquals("Evans123", teamDto.getJoinToken());
    }

    @Test
    void getCreatorId() {
        assertEquals("Mark", teamDto.getCreatorId());
        assertNotEquals(2L, teamDto.getId());
    }

    @Test
    void setCreatorId() {
        teamDto.setCreatorId("Todd");
        assertEquals("Todd", teamDto.getCreatorId());
    }

    @Test
    void getUsers() {
        assertEquals(users, teamDto.getUsers());
        final String username = "Shawn";
        final int role = 1;
        final UserDto userDto = new UserDto(username, role);
        Set<UserDto> users2 = new HashSet<>();
        users2.add(userDto);
        assertNotEquals(users2, teamDto.getUsers());
    }

    @Test
    void setUsers() {
        Set<UserDto> users2 = new HashSet<>();
        assertEquals(users, teamDto.getUsers());
        final String username = "Shawn";
        final int role = 1;
        final UserDto userDto = new UserDto(username, role);
        users2.add(userDto);
        teamDto.setUsers(users2);
        assertEquals(users2, teamDto.getUsers());
    }

    @Test
    void testSameObjectEquals() {
        assertTrue(teamDto.equals(teamDto));
    }

    @Test
    void testEqualsNull() {
        assertFalse(teamDto.equals(null));
    }

    @Test
    void testEqualsTrue() {
        TeamDto teamDto2 = new TeamDto(id, name, joinToken, creatorId);
        assertEquals(teamDto, teamDto2);
    }

    @Test
    void testEqualsClassNotTheSame() {
        UserDto userDto = new UserDto();
        assertNotEquals(teamDto, userDto);
    }

    @Test
    void testEqualsIdNotSame() {
        TeamDto teamDto2 = new TeamDto(10L, "Royal Academy", "Kidou123", "Jude");
        assertNotEquals(teamDto, teamDto2);
    }

    @Test
    void testEqualsNameBothDifferent() {
        TeamDto teamDto2 = new TeamDto(id, "Royal Academy", "Kidou123", "Jude");
        TeamDto teamDto3 = new TeamDto(id, "Royal Academy", "Kidou123", "Jude");
        TeamDto teamDto4 = new TeamDto(id, "Royal Academy plus", "Kidou123", "Jude");
        assertTrue(teamDto3.equals(teamDto2));
        assertTrue(teamDto2.equals(teamDto3));
        assertFalse(teamDto2.equals(teamDto4));
        assertFalse(teamDto4.equals(teamDto2));
    }

    @Test
    void testEqualsNameNotSame() {
        final TeamDto teamDto2 = new TeamDto(id, null, "Kidou123", "Jude");
        final TeamDto teamDto3 = new TeamDto(id, "Royal Academy", "Kidou123", "Jude");
        final TeamDto teamDto4 = new TeamDto(id, null, "Kidou123", "Jude");
        assertTrue(teamDto2.equals(teamDto2));
        assertTrue(teamDto2.equals(teamDto4));
        assertTrue(teamDto4.equals(teamDto2));
        assertFalse(teamDto.equals(teamDto2));
        assertFalse(teamDto2.equals(teamDto));
        assertFalse(teamDto.equals(teamDto3));
        assertFalse(teamDto3.equals(teamDto));
    }

    @Test
    void testEqualsJoinTokenNotSame() {
        final TeamDto teamDto2 = new TeamDto(id, name, null, "Jude");
        final TeamDto teamDto3 = new TeamDto(id, name, "Kidou123", "Jude");
        final TeamDto teamDto4 = new TeamDto(id, name, null, "Jude");

        assertTrue(teamDto2.equals(teamDto2));
        assertTrue(teamDto2.equals(teamDto4));
        assertTrue(teamDto4.equals(teamDto2));
        assertFalse(teamDto.equals(teamDto2));
        assertFalse(teamDto2.equals(teamDto));
        assertFalse(teamDto.equals(teamDto3));
        assertFalse(teamDto3.equals(teamDto));
    }

    @Test
    void testEqualsCreatorIdNotSame() {
        final TeamDto teamDto2 = new TeamDto(id, name, joinToken, null);
        final TeamDto teamDto3 = new TeamDto(id, name, joinToken, "Jude");
        final TeamDto teamDto4 = new TeamDto(id, name, joinToken, null);

        assertTrue(teamDto2.equals(teamDto2));
        assertTrue(teamDto2.equals(teamDto4));
        assertTrue(teamDto4.equals(teamDto2));
        assertFalse(teamDto.equals(teamDto2));
        assertFalse(teamDto2.equals(teamDto));
        assertFalse(teamDto.equals(teamDto3));
        assertFalse(teamDto3.equals(teamDto));
    }

    @Test
    void testHashCode() {
        TeamDto teamDto2 = new TeamDto(id, name, joinToken, creatorId);
        assertTrue(teamDto.equals(teamDto2) && teamDto2.equals(teamDto));
        assertTrue(teamDto.hashCode() == teamDto2.hashCode());
    }

    @Test
    void testHashCodeNameNull() {
        TeamDto teamDto2 = new TeamDto(id, null, joinToken, creatorId);
        assertTrue(teamDto2.hashCode() == teamDto2.hashCode());
    }

    @Test
    void testHashCodeJointokenNull() {
        TeamDto teamDto2 = new TeamDto(id, name, null, creatorId);
        assertTrue(teamDto2.hashCode() == teamDto2.hashCode());
    }

    @Test
    void testHashCodeCreatorIdNull() {
        TeamDto teamDto2 = new TeamDto(id, name, joinToken, null);
        assertTrue(teamDto2.hashCode() == teamDto2.hashCode());
    }

    @Test
    void testToString() {
        String toString = teamDto.toString();
        assertTrue(toString.contains("TeamDto{id=" + id));
    }

}
