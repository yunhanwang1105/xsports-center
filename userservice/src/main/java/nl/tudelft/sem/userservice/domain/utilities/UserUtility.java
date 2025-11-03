package nl.tudelft.sem.userservice.domain.utilities;

import java.util.Set;
import java.util.stream.Collectors;
import nl.tudelft.sem.userservice.domain.User;
import nl.tudelft.sem.userservice.domain.UserDto;

public class UserUtility {

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
}
