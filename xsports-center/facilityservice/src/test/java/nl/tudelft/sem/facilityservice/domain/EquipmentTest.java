package nl.tudelft.sem.facilityservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EquipmentTest {

    private final String id = "12";
    private final String name = "Puck";
    private final String name2 = "Hockey";
    private final int min = 2;
    private final int max = 20;
    private final Sport sport = new Sport(new SportDto(name2, min, max));
    private final Set<Sport> sports = new HashSet<>();
    private final Equipment equipment = new Equipment(new EquipmentDto(id, name, sports));
    private final Equipment equipment2 = new Equipment(new EquipmentDto(id, name, sports));
    private final Equipment equipment3 = new Equipment(new EquipmentDto(id, "Goal", sports));

    @Test
    void getEquipmentDto() {
        assertEquals(equipment.getEquipmentDto(), equipment2.getEquipmentDto());
    }

    @Test
    void emptyConstructor() {
        Equipment e = new Equipment();
        assertEquals(e.getEquipmentId(), null);
        assertEquals(e.getName(), null);
        assertEquals(e.getSports(), null);
    }

    @Test
    void testEquals() {
        assertEquals(equipment, equipment2);
        assertNotEquals(equipment, equipment3);
    }

    @Test
    public void testEquals_Symmetric() {
        assertTrue(equipment.equals(equipment2) && equipment2.equals(equipment));
    }

    @Test
    void getEquipment_id() {
        assertEquals(id, equipment.getEquipmentId());
    }

    @Test
    void setEquipment_id() {
        Equipment e = new Equipment(new EquipmentDto(id, name, sports));
        e.setEquipmentId("test");
        assertEquals("test", e.getEquipmentId());
    }

    @Test
    void getName() {
        assertEquals(name, equipment.getName());
    }

    @Test
    void setName() {
        Equipment e = new Equipment(new EquipmentDto(id, name, sports));
        e.setName("test");
        assertEquals("test", e.getName());
    }

    @Test
    void getSports() {
        assertEquals(sports, equipment.getSports());
    }

    @Test
    void setSports() {
        Set<Sport> s = new HashSet<>();
        s.add(new Sport(new SportDto("karting", 1, 20)));

        Equipment e = new Equipment(new EquipmentDto(id, name, sports));
        e.setSports(s);
        assertEquals(s, e.getSports());
    }

    @Test
    void testToString() {
        Equipment e = new Equipment(new EquipmentDto("ID", "NAME", null));
        assertEquals("Equipment{equipment_id='ID', name='NAME', sports:null}", e.toString());
    }
}