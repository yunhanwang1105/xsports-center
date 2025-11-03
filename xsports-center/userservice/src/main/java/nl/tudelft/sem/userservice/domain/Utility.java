package nl.tudelft.sem.userservice.domain;

import java.util.Set;
import java.util.stream.Collectors;
import nl.tudelft.sem.userservice.domain.utilities.UserUtility;

public class Utility {
    /**
     * This method converts a user object to an userDto object.
     *
     * @param user The user to be converted
     * @return The userDto object
     */
    public static UserDto mapUserToUserDto(User user) {
        UserDto userDto = new UserDto(user.getUsername(), user.getRole());

        if (user.getTeams() == null) {
            return userDto;
        }

        Set<String> teamIds = user.getTeams().stream().map(t -> String.valueOf(t.getId()))
            .collect(Collectors.toSet());

        userDto.setTeamIds(teamIds);

        return userDto;
    }

    /**
     * This method converts a team object to an teamDto object.
     *
     * @param team The team to be converted
     * @return The teamDto object
     */
    public static TeamDto mapTeamToTeamDto(Team team) {
        TeamDto teamDto =
            new TeamDto(team.getId(), team.getName(), team.getJoinToken(), team.getCreatorId());

        if (team.getUsers() == null) {
            return teamDto;
        }

        Set<UserDto> userDtoSet =
            team.getUsers().stream().map(UserUtility::mapUserToUserDto).collect(Collectors.toSet());

        teamDto.setUsers(userDtoSet);

        return teamDto;

    }

}
