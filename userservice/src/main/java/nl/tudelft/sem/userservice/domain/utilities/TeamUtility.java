package nl.tudelft.sem.userservice.domain.utilities;

import java.util.Set;
import java.util.stream.Collectors;
import nl.tudelft.sem.userservice.domain.Team;
import nl.tudelft.sem.userservice.domain.TeamDto;
import nl.tudelft.sem.userservice.domain.UserDto;

public class TeamUtility {

    /**
     * This method converts a team object to an teamDto object.
     *
     * @param team The team to be converted
     * @return The teamDto object
     */
    public static TeamDto mapTeamToTeamDto(Team team) {
        TeamDto teamDto = new TeamDto(team.getId(), team.getName(), null, team.getCreatorId());

        if (team.getUsers() == null) {
            return teamDto;
        }

        Set<UserDto> userDtoSet =
            team.getUsers().stream().map(UserUtility::mapUserToUserDto).collect(Collectors.toSet());

        teamDto.setUsers(userDtoSet);

        return teamDto;

    }

}
