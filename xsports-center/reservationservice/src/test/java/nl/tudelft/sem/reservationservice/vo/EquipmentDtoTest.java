package nl.tudelft.sem.reservationservice.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EquipmentDtoTest {

    String equipmentId;
    String name;
    Set<SportDto> sports;
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
        sports.add(new SportDto(name2, minPlayers, maxPlayers));
        equipmentDto = new EquipmentDto(equipmentId, name, sports);
    }

    // Test get equipment id.
    @Test
    void getId() {
        assertEquals(equipmentDto.getEquipmentId(), equipmentId);
    }

    // Test set equipment id.
    @Test
    void setId() {
        equipmentDto.setEquipmentId("2");
        assertEquals(equipmentDto.getEquipmentId(), "2");
    }

    // Test get equipment name.
    @Test
    void getName() {
        assertEquals(equipmentDto.getName(), name);
    }

    // Test set equipment name.
    @Test
    void setName() {
        equipmentDto.setName("Net");
        assertEquals(equipmentDto.getName(), "Net");
    }

    // Test get sports relating to the equipment.
    @Test
    void getSports() {
        assertEquals(equipmentDto.getSports(), sports);
    }

    // Test set sports of the equipment.
    @Test
    void setSports() {
        Set<SportDto> sport2 = new HashSet<SportDto>();
        sport2.add(new SportDto("tennis", 2, 4));
        equipmentDto.setSports(sport2);
        assertEquals(equipmentDto.getSports(), sport2);
    }

    // Test equals method.
    @Test
    void testEquals() {
        EquipmentDto equipmentDto2 = new EquipmentDto(equipmentId, name, sports);
        assertEquals(equipmentDto, equipmentDto2);
        assertEquals(equipmentDto, equipmentDto);
    }

    // Test equals method when id differs.
    @Test
    void notEqualId() {
        String id = "2";
        EquipmentDto equipmentDto2 = new EquipmentDto(id, name, sports);
        assertNotEquals(equipmentDto, equipmentDto2);
        assertFalse(equipmentDto.equals("s"));
        assertFalse(equipmentDto.equals(null));
    }

    // Test hashCode.
    @Test
    void testHashCode() {
        int code = equipmentDto.hashCode();
        assertEquals(code, equipmentDto.hashCode());
    }

    // Test toString.
    @Test
    void testToString() {
        String s = equipmentDto.toString();
        assertEquals(s, equipmentDto.toString());
    }
}