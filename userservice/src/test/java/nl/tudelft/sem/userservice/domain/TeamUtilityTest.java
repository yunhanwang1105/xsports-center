package nl.tudelft.sem.userservice.domain;

import static nl.tudelft.sem.userservice.domain.utilities.TeamUtility.mapTeamToTeamDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class TeamUtilityTest {

    // for teamDto
    private final Long id = 1L;
    private final String name = "Raimon";
    private final String joinToken = "Endou123";
    private final String creatorId = "Mark";
    private final TeamDto teamDto = new TeamDto(id, name, null, creatorId);
    private final Set<UserDto> userDtoSet = new HashSet<>();
    private final Team team = new Team(id, name, joinToken, creatorId, null);
    // for team
    private final Set<User> usersSet = new HashSet<>();
    private final Team team2 = new Team(id, name, joinToken, creatorId, usersSet);

    @Test
    public void mapTeamtoTeamDtoTest() {
        assertEquals(teamDto, mapTeamToTeamDto(team2));
    }

    @Test
    public void mapTeamtoTeamDtoTestButSetNull() {
        assertEquals(teamDto, mapTeamToTeamDto(team));
    }
}
