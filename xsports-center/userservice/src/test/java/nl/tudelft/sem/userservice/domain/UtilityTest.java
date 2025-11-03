package nl.tudelft.sem.userservice.domain;

import static nl.tudelft.sem.userservice.domain.Utility.mapTeamToTeamDto;
import static nl.tudelft.sem.userservice.domain.Utility.mapUserToUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class UtilityTest {
    // for userDto
    private final String username = "name";
    private final int role = 0;
    private final String password = "abc";
    private final UserDto userDto = new UserDto(username, role);
    private final UserDto userDtoWithPassword = new UserDto(username, password);
    private final User userSetNull = new User(username, role, null);
    // for teamDto
    private final Long id = 1L;
    private final String name = "Raimon";
    private final String joinToken = "Endou123";
    private final String creatorId = "Mark";
    private final TeamDto teamDto = new TeamDto(id, name, joinToken, creatorId);
    private final Set<UserDto> userDtoSet = new HashSet<>();
    private final Team team = new Team(id, name, joinToken, creatorId, null);
    // for user
    private final Set<Team> set = new HashSet<>();
    private final User user = new User(username, role, set);
    // for team
    private final Set<User> usersSet = new HashSet<>();
    private final Team team2 = new Team(id, name, joinToken, creatorId, usersSet);

    @Test
    public void mapUserToUserDtoTest() {
        assertEquals(userDto, mapUserToUserDto(user));
    }

    @Test
    public void mapUserToUserDtoTestButSetNull() {
        assertEquals(userDto, mapUserToUserDto(userSetNull));
    }

    @Test
    public void mapTeamtoTeamDtoTest() {
        assertEquals(teamDto, mapTeamToTeamDto(team2));
    }

    @Test
    public void mapTeamtoTeamDtoTestButSetNull() {
        assertEquals(teamDto, mapTeamToTeamDto(team));
    }

}
