package nl.tudelft.sem.userservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TeamRepositoryTest {

    private final Long id = 1L;
    private final String name = "Raimon";
    private final String joinToken = "Endou123";
    private final String creatorId = "Mark";
    private Set<User> users;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void getTeamNullTest() {
        assertEquals(Optional.empty(), teamRepository.findById(id));
    }

    @Test
    public void saveTeamTest() {
        Team team = new Team(id, name, joinToken, creatorId, users);
        assertEquals(team, teamRepository.save(team));
        assertNotNull(teamRepository.findAll());
    }

}
