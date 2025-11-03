package nl.tudelft.sem.facilityservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EquipmentDtoTest {

    String equipmentId;
    String name;
    Set<Sport> sports;
    String name2;
    int minPlayers;
    int maxPlayers;
    EquipmentDto equipmentDto;

    @BeforeEach
    void setUp() {
        equipmentId = "1";
        name = "Ball";
        name2 = "Football";
        minPlayers = 2;
        maxPlayers = 20;
        sports = new HashSet<>();
        sports.add(new Sport(name2, minPlayers, maxPlayers));
        equipmentDto = new EquipmentDto(equipmentId, name, sports);
    }

    @Test
    void emptyConstructor() {
        EquipmentDto e = new EquipmentDto();
        assertEquals(e.getId(), null);
        assertEquals(e.getName(), null);
        assertEquals(e.getSports(), null);
    }

    @Test
    void getId() {
        assertEquals(equipmentDto.getId(), equipmentId);
    }

    @Test
    void setId() {
        equipmentDto.setEquipmentId("2");
        assertEquals(equipmentDto.getId(), "2");
    }

    @Test
    void getName() {
        assertEquals(equipmentDto.getName(), name);
    }

    @Test
    void setName() {
        equipmentDto.setName("Net");
        assertEquals(equipmentDto.getName(), "Net");
    }

    @Test
    void getSport() {
        assertEquals(equipmentDto.getSports(), sports);
    }

    @Test
    void setSport() {
        Set<Sport> sport2 = new HashSet<Sport>();
        sport2.add(new Sport("tennis", 2, 4));
        equipmentDto.setSport(sport2);
        assertEquals(equipmentDto.getSports(), sport2);
    }

    @Test
    void testEquals() {
        EquipmentDto equipmentDto2 = new EquipmentDto(equipmentId, name, sports);
        assertTrue(equipmentDto.equals(equipmentDto2));
    }
}