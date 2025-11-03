package nl.tudelft.sem.facilityservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SportTest {
    Sport sport;
    String name = "SPORT";
    int min = 1;
    int max = 2;

    @BeforeEach
    void setup() {
        sport = new Sport(new SportDto(name, min, max));
    }

    @Test
    void emptyConstructor() {
        sport = new Sport();
        assertEquals(null, sport.getName());
    }

    @Test
    void getSportDto() {
        SportDto sd = new SportDto(name, min, max);
        assertEquals(sd, sport.getSportDto());
    }

    @Test
    void testEquals() {
        Sport s2 = new Sport(new SportDto(name, min, max));
        Sport s3 = new Sport(new SportDto(name, min, 500));

        assertTrue(sport.equals(s2));
        assertFalse(sport.equals(s3));
    }

    @Test
    void testHashCode() {
        assertEquals(Objects.hash(name, min, max), sport.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Sport{name='SPORT', minPlayers=1, playerMax=2}", sport.toString());
    }

    @Test
    void getName() {
        assertEquals(name, sport.getName());
    }

    @Test
    void setName() {
        sport.setName("Fishing");
        assertEquals("Fishing", sport.getName());
    }

    @Test
    void getMinPlayers() {
        assertEquals(min, sport.getMinPlayers());
    }

    @Test
    void setMinPlayers() {
        sport.setMinPlayers(69);
        assertEquals(69, sport.getMinPlayers());
    }

    @Test
    void getPlayerMax() {
        assertEquals(max, sport.getPlayerMax());
    }

    @Test
    void setPlayerMax() {
        sport.setPlayerMax(42);
        assertEquals(42, sport.getPlayerMax());
    }
}