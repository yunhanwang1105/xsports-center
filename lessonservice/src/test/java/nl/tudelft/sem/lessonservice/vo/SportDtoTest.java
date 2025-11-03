package nl.tudelft.sem.lessonservice.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SportDtoTest {
    private String name;

    private int minPlayers;

    private int maxPlayers;

    private SportDto sportDto;

    @BeforeEach
    void setUp() {
        name = "Rugby";
        minPlayers = 8;
        maxPlayers = 16;
        sportDto = new SportDto(name, minPlayers, maxPlayers);
    }

    @Test
    void getName() {
        assertEquals(name, sportDto.getName());
    }

    @Test
    void getMinPlayers() {
        assertEquals(minPlayers, sportDto.getMinPlayers());
    }

    @Test
    void getMaxPlayers() {
        assertEquals(maxPlayers, sportDto.getMaxPlayers());
    }

    @Test
    void setName() {
        sportDto.setName("football");
        assertEquals("football", sportDto.getName());
    }

    @Test
    void setMinPlayers() {
        sportDto.setMinPlayers(2);
        assertEquals(2, sportDto.getMinPlayers());
    }

    @Test
    void setMaxPlayers() {
        sportDto.setMaxPlayers(20);
        assertEquals(20, sportDto.getMaxPlayers());
    }

    @Test
    void testEquals() {
        SportDto sportDto1 = new SportDto(name, minPlayers, maxPlayers);
        assertEquals(sportDto1, sportDto);
        SportDto sportDto2 = new SportDto("vollyball", minPlayers, maxPlayers);
        assertNotEquals(sportDto2, sportDto);

    }

    @Test
    void testHashCode() {
        SportDto sportDto1 = new SportDto(name, minPlayers, maxPlayers);
        assertEquals(sportDto1.hashCode(), sportDto.hashCode());

    }
}