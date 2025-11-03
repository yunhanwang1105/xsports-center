package nl.tudelft.sem.reservationservice.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

    // Test get sport name.
    @Test
    void getName() {
        assertEquals(name, sport.getName());
    }

    // Test set sport name.
    @Test
    void setName() {
        sport.setName("Volleyball");
        assertEquals("Volleyball", sport.getName());
    }

    // Test get min players of the sport.
    @Test
    void getMinPlayers() {
        assertEquals(minPlayers, sport.getMinPlayers());
    }

    // Test set min players of the sport.
    @Test
    void setMinPlayers() {
        sport.setMinPlayers(4);
        assertEquals(4, sport.getMinPlayers());
    }

    // Test get max players of the sport.
    @Test
    void getMaxPlayers() {
        assertEquals(maxPlayers, sport.getMaxPlayers());
    }

    // Test set max players of the sport.
    @Test
    void setMaxPlayers() {
        sport.setMaxPlayers(16);
        assertEquals(16, sport.getMaxPlayers());
    }

    // Test equals.
    @Test
    void testEquals() {
        assertEquals(sport, new SportDto("Football", 2, 20));
        assertEquals(sport, sport);
        assertNotEquals(sport, new SportDto("Volleyball", 12, 26));
    }

    // Test not equals 2.
    @Test
    void testEquals2() {
        assertNotEquals(new SportDto("handball", 1, 20), sport);
        assertFalse(sport.equals(null));
        assertFalse(sport.equals("s"));
    }

    // Test hashCode.
    @Test
    void testHashCode() {
        int code = sport.hashCode();
        assertEquals(code, sport.hashCode());
    }

    // Test toString
    @Test
    void testToString() {
        String s = sport.toString();
        assertEquals(s, sport.toString());
    }
}