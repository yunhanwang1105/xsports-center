package nl.tudelft.sem.userservice.domain;

import nl.tudelft.sem.userservice.domain.exceptions.ForbiddenException;
import nl.tudelft.sem.userservice.domain.exceptions.IncorrectParameterException;
import nl.tudelft.sem.userservice.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Team service.
 */
@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a team in the database.
     *
     * @param team The team to be saved
     * @return The saved team
     */
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    /**
     * Let a user join a team.
     *
     * @param teamId    The id of the team
     * @param username  The username of the user
     * @param joinToken The join token
     * @return True iff the joining is successful
     * @throws NotFoundException           if the team or the user is not found (404)
     * @throws IncorrectParameterException if the join token is incorrect (403)
     * @throws ForbiddenException          if the user already joined the team before or an admin
     *                                     wants to join (403)
     */
    public boolean joinTeam(Long teamId, String username, String joinToken) {
        Team team = teamRepository.findTeamById(teamId);
        User user = userRepository.findByUsername(username);

        // Check if the user exists
        if (user == null) {
            throw new NotFoundException("User not found, cannot join team");
        }

        // Check if the user is an admin
        if (user.getRole() == 0) {
            throw new ForbiddenException("Admin cannot join a team");
        }

        // Check if the team exists
        if (team == null) {
            throw new NotFoundException("Team not found, cannot join team");
        }

        // check if the joinToken is correct
        if (!team.getJoinToken().equals(joinToken)) {
            throw new IncorrectParameterException("Incorrect join token, cannot join the team");
        }

        // Check if the user is already in the team
        if (team.getUsers().contains(user)) {
            throw new ForbiddenException("User already joined team, cannot join the team");
        }

        team.getUsers().add(user);
        user.getTeams().add(team);

        teamRepository.save(team);
        userRepository.save(user);

        return true;
    }

    /**
     * Gets a team.
     *
     * @param teamId The id of the team to get
     * @return The team
     * @throws NotFoundException if the requested team is not found (404)
     */
    public Team getTeam(Long teamId) {
        Team team = teamRepository.findTeamById(teamId);
        if (team == null) {
            throw new NotFoundException("The requested team is not found");
        }
        return teamRepository.findTeamById(teamId);
    }

    /**
     * Allow users to exit team.
     *
     * @param teamId   the team id
     * @param username the username
     * @return the boolean
     */
    public Boolean exitTeam(Long teamId, String username) {

        Team team = teamRepository.findTeamById(teamId);
        User user = userRepository.findByUsername(username);

        // Check if the user exists
        if (user == null) {
            throw new NotFoundException("User not found, cannot exit from team");
        }

        // Check if the team exists
        if (team == null) {
            throw new NotFoundException("Team not found, cannot exit from the team");
        }

        // Check if the user is already in the team
        if (!team.getUsers().contains(user)) {
            throw new ForbiddenException("User not in team, cannot exit from the team");
        }

        team.getUsers().remove(user);
        user.getTeams().remove(team);

        teamRepository.save(team);
        userRepository.save(user);

        return true;
    }
}
