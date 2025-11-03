package nl.tudelft.sem.userservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

public class TeamTest {

    private final Long id = 1L;
    private final String name = "Raimon";
    private final String joinToken = "Endou123";
    private final String creatorId = "Mark";
    private Set<User> users;

    private final Team team = new Team(id, name, joinToken, creatorId, users);
    private final Team team2 = new Team(id, name, joinToken, creatorId, users);
    private final Team team3 = new Team(10L, "Royal Academy", "Kidou123", "Jude", users);
    private final Team team4 = new Team(null, "Royal Academy", "Kidou123", "Jude", users);

    @Test
    public void equalsTest() {
        assertEquals(team, team2);
        assertNotEquals(team, team3);
    }

    @Test
    public void equalsTestNull() {
        assertFalse(team.equals(null));
        assertNotEquals(team, null);
    }

    @Test
    public void equalsTestClassNotTheSame() {
        assertFalse(team.equals(new TeamDto()));
        assertNotEquals(team, new TeamDto());
    }

    @Test
    public void equalsTestIdNull() {
        assertFalse(team4.equals(team));
        assertNotEquals(team, team4);
    }

    @Test
    public void testEquals_Symmetric() {
        assertTrue(team2.equals(team) && team.equals(team2));
        assertTrue(team2.hashCode() == team.hashCode());
    }
}
