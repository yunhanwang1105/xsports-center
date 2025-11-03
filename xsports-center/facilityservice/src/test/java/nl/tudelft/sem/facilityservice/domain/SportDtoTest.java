package nl.tudelft.sem.facilityservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SportDtoTest {
    SportDto sport;
    private String name;
    private int minPlayers;
    private int maxPlayers;

    @BeforeEach
    void setUp() {
        name = "Football";
        minPlayers = 2;
        maxPlayers = 20;
        sport = new SportDto(name, minPlayers, maxPlayers);
    }

    @Test
    void getName() {
        assertEquals(name, sport.getName());
    }

    @Test
    void setName() {
        sport.setName("Volleyball");
        assertEquals("Volleyball", sport.getName());
    }

    @Test
    void getMinPlayers() {
        assertEquals(minPlayers, sport.getMinPlayers());
    }

    @Test
    void setMinPlayers() {
        sport.setMinPlayers(4);
        assertEquals(4, sport.getMinPlayers());
    }

    @Test
    void getMaxPlayers() {
        assertEquals(maxPlayers, sport.getMaxPlayers());
    }

    @Test
    void setMaxPlayers() {
        sport.setMaxPlayers(16);
        assertEquals(16, sport.getMaxPlayers());
    }

    @Test
    void testEquals() {
        assertTrue(sport.equals(new SportDto("Football", 2, 20)));
        assertFalse(sport.equals(new SportDto("Volleyball", 12, 26)));
    }

    @Test
    void testToString() {
        assertEquals("SportDto{name='Football', minPlayers=2, maxPlayers=20}", sport.toString());
    }
}