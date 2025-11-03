package nl.tudelft.sem.userservice.application;

import static nl.tudelft.sem.userservice.domain.communication.ServiceCommunication.isAuthenticated;

import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.userservice.domain.Team;
import nl.tudelft.sem.userservice.domain.TeamDto;
import nl.tudelft.sem.userservice.domain.TeamService;
import nl.tudelft.sem.userservice.domain.User;
import nl.tudelft.sem.userservice.domain.UserDto;
import nl.tudelft.sem.userservice.domain.UserService;
import nl.tudelft.sem.userservice.domain.Utility;
import nl.tudelft.sem.userservice.domain.exceptions.ForbiddenException;
import nl.tudelft.sem.userservice.domain.exceptions.IncorrectParameterException;
import nl.tudelft.sem.userservice.domain.exceptions.NotFoundException;
import nl.tudelft.sem.userservice.domain.utilities.TeamUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
@Slf4j
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    /**
     * POST: Create a team.
     * URL: localhost:9191/teams/create?username=???&password=???
     *
     * @param teamDto  The TeamDto object containing the information to create a team
     * @param username the username of the user that wants to create the team
     * @param password the password of the user that wants to create the team
     * @return The teamDto of the team just created
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public TeamDto createTeam(@RequestBody TeamDto teamDto, String username, String password) {

        if (!isAuthenticated(new UserDto(username, password))) {
            log.error("Credentials are incorrect");
            return null;
        }

        User user = userService.findByUsername(username);
        if (user == null) {
            log.error("User was null");
            return null;
        }

        if (user.getRole() == 0) {
            throw new ForbiddenException("Admin cannot create a team");
        }

        Team team = new Team();
        team.setName(teamDto.getName());
        team.setJoinToken(teamDto.getJoinToken());
        team.setCreatorId(user.getUsername());
        team.setUsers(new HashSet<>());
        team.getUsers().add(user);
        Team savedTeam = teamService.saveTeam(team);

        user.getTeams().add(savedTeam);
        userService.saveUser(user);

        TeamDto returnTeam = TeamUtility.mapTeamToTeamDto(team);
        returnTeam.setJoinToken(team.getJoinToken());
        return returnTeam;
    }

    /**
     * GET: Join a team.
     * URL: localhost:9191/teams/join?teamId=???&joinToken=???&username=???&password=???
     *
     * @param teamId    The id of the team to be joined
     * @param username  The username of the user who wants to join a team
     * @param joinToken The join token to join the team
     * @param password  The password of the user that wants to join the team
     * @return True iff the joining is successful
     * @throws NotFoundException           if the team or the user is not found (404)
     * @throws IncorrectParameterException if the join token is incorrect (403)
     * @throws ForbiddenException          if the user already joined the team before or an admin
     *                                     wants to join (403)
     */
    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public Boolean joinTeam(Long teamId, String joinToken, String username, String password) {

        if (!isAuthenticated(new UserDto(username, password))) {
            log.error("Credentials are incorrect");
            return null;
        }

        return teamService.joinTeam(teamId, username, joinToken);
    }

    /**
     * GET: Get the information of a team.
     * URL: localhost:9191/teams/{teamId}
     *
     * @param teamId The id of the team
     * @return The teamDto of the team
     * @throws NotFoundException if the requested team is not found (404)
     */
    @RequestMapping(value = "/{teamId}", method = RequestMethod.GET)
    public TeamDto getTeam(@PathVariable("teamId") Long teamId) {

        Team team = teamService.getTeam(teamId);

        return Utility.mapTeamToTeamDto(team);
    }

    /**
     * Delete: Remove the user from the team.
     * URL: localhost:9191/teams/exit?teamId=???&username=???&password=???
     *
     * @param teamId The id of the team
     * @return The teamDto of the team
     * @throws NotFoundException if the requested team is not found (404)
     */
    @RequestMapping(value = "/exit", method = RequestMethod.DELETE)
    public Boolean exitTeam(Long teamId, String username, String password) {

        if (!isAuthenticated(new UserDto(username, password))) {
            log.error("Credentials are incorrect");
            return null;
        }

        return teamService.exitTeam(teamId, username);
    }

}
