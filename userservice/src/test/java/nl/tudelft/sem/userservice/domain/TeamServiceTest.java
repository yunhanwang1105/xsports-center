package nl.tudelft.sem.userservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import nl.tudelft.sem.userservice.domain.exceptions.ForbiddenException;
import nl.tudelft.sem.userservice.domain.exceptions.IncorrectParameterException;
import nl.tudelft.sem.userservice.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TeamServiceTest {

    private final Long id = 1L;
    private final String name = "Raimon";
    private final String joinToken = "Endou123";
    private final String creatorId = "Mark";
    private final String username = "name";
    private final int role = 1;
    @InjectMocks
    private final TeamService teamService = new TeamService();
    private final Set<User> users = new HashSet<>();
    private final Team team = new Team(id, name, joinToken, creatorId, users);
    private final Set<User> users2 = new HashSet<>();
    private final Set<Team> set = new HashSet<>();
    private final User user = new User(username, role, set);
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveTeamTest() {
        when(teamRepository.save(team)).thenReturn(team);
        assertEquals(team, teamService.saveTeam(team));
    }

    @Test
    public void joinTeamTest() {
        when(teamRepository.findTeamById(id)).thenReturn(team);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(teamRepository.save(team)).thenReturn(team);
        when(userRepository.save(user)).thenReturn(user);
        team.setUsers(users);
        assertEquals(true, teamService.joinTeam(id, username, "Endou123"));
    }

    @Test
    public void joinTeamTestButTeamNull() {
        when(teamRepository.findTeamById(id)).thenReturn(null);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(teamRepository.save(team)).thenReturn(team);
        when(userRepository.save(user)).thenReturn(user);
        assertThrows(NotFoundException.class, () -> teamService.joinTeam(id, username, "Endou123"));
    }

    @Test
    public void joinTeamTestButUserNull() {
        when(teamRepository.findTeamById(id)).thenReturn(team);
        when(userRepository.findByUsername(username)).thenReturn(null);
        when(teamRepository.save(team)).thenReturn(team);
        when(userRepository.save(user)).thenReturn(user);
        assertThrows(NotFoundException.class, () -> teamService.joinTeam(id, username, "Endou123"));
    }

    @Test
    public void joinTeamTestButJointokenIncorrect() {
        when(teamRepository.findTeamById(id)).thenReturn(team);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(teamRepository.save(team)).thenReturn(team);
        when(userRepository.save(user)).thenReturn(user);
        assertThrows(IncorrectParameterException.class,
            () -> teamService.joinTeam(id, username, "Endou321"));
    }

    @Test
    public void joinTeamTestButUserAlreadyInTeam() {
        users2.add(user);
        team.setUsers(users2);
        when(teamRepository.findTeamById(id)).thenReturn(team);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(teamRepository.save(team)).thenReturn(team);
        when(userRepository.save(user)).thenReturn(user);
        assertThrows(ForbiddenException.class,
            () -> teamService.joinTeam(id, username, "Endou123"));
    }

    @Test
    public void joinTeamTestButUserIsAdmin() {
        User admin = new User(username, 0, set);
        users2.add(admin);
        team.setUsers(users2);
        when(teamRepository.findTeamById(id)).thenReturn(team);
        when(userRepository.findByUsername(username)).thenReturn(admin);
        when(teamRepository.save(team)).thenReturn(team);
        when(userRepository.save(admin)).thenReturn(admin);
        assertThrows(ForbiddenException.class,
            () -> teamService.joinTeam(id, username, "Endou123"));
    }

    @Test
    public void getTeamTest() {
        when(teamRepository.findTeamById(id)).thenReturn(team);
        assertEquals(team, teamService.getTeam(id));
    }

    @Test
    public void getTeamTestNull() {
        when(teamRepository.findTeamById(id)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> teamService.getTeam(id));
    }

    // Test if a user can exit from a team that contains the user.
    @Test
    public void exitTeamTest() {
        Set<User> members = new HashSet<>();
        Team theTeam = new Team(id, name, joinToken, creatorId, members);
        Set<Team> teams = new HashSet<>(Set.of(theTeam));
        User theUser = new User(username, role, teams);
        members.add(theUser);

        when(teamRepository.findTeamById(id)).thenReturn(theTeam);
        when(userRepository.findByUsername(username)).thenReturn(theUser);

        boolean result = teamService.exitTeam(id, username);

        assertTrue(result);
        assertEquals(0, theTeam.getUsers().size());
        assertEquals(0, theUser.getTeams().size());
    }

    // Test Not found exception is thrown when user wants to exit from a team and the
    // team does not exist.
    @Test
    public void exitTeamTestNoSuchTeam() {
        Set<User> members = new HashSet<>();
        Team theTeam = new Team(id, name, joinToken, creatorId, members);
        Set<Team> teams = new HashSet<>(Set.of(theTeam));
        User theUser = new User(username, role, teams);
        members.add(theUser);

        when(teamRepository.findTeamById(id)).thenReturn(null);
        when(userRepository.findByUsername(username)).thenReturn(theUser);

        assertThrows(NotFoundException.class, () -> teamService.exitTeam(id, username));
    }

    // Test Not found exception is thrown when user wants to exit from a team and the
    // user does not exist in our database.
    @Test
    public void exitTeamTestNoSuchUser() {
        Set<User> members = new HashSet<>();
        Team theTeam = new Team(id, name, joinToken, creatorId, members);
        Set<Team> teams = new HashSet<>(Set.of(theTeam));
        User theUser = new User(username, role, teams);
        members.add(theUser);

        when(teamRepository.findTeamById(id)).thenReturn(theTeam);
        when(userRepository.findByUsername(username)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> teamService.exitTeam(id, username));
    }

    // Test Forbidden exception is thrown when user wants to exit from a team and the
    // user does not exist in the team.
    @Test
    public void exitTeamTestUserNotInTeam() {
        Set<User> members = new HashSet<>();
        Team theTeam = new Team(id, name, joinToken, creatorId, members);
        Set<Team> teams = new HashSet<>();
        User theUser = new User(username, role, teams);

        when(teamRepository.findTeamById(id)).thenReturn(theTeam);
        when(userRepository.findByUsername(username)).thenReturn(theUser);

        assertThrows(ForbiddenException.class, () -> teamService.exitTeam(id, username));
    }

}
