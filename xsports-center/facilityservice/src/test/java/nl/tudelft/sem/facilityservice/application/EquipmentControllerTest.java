package nl.tudelft.sem.facilityservice.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import nl.tudelft.sem.facilityservice.domain.Equipment;
import nl.tudelft.sem.facilityservice.domain.EquipmentDto;
import nl.tudelft.sem.facilityservice.domain.EquipmentRepository;
import nl.tudelft.sem.facilityservice.domain.Sport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EquipmentControllerTest {

    private final Set<Sport> sports = new HashSet<>();
    private final String id = "123";
    private final String name = "Ball";
    private final Equipment equipment = new Equipment(new EquipmentDto(id, name, sports));

    @Mock
    private EquipmentRepository equipmentRepository;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveEquipment() {
        when(equipmentRepository.save(equipment)).thenReturn(equipment);
        assertEquals(equipment, equipmentRepository.save(equipment));
        assertNotNull(equipmentRepository.findAll());
    }

    @Test
    void getEquipmentByName() {
        when(equipmentRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(equipment));
        assertEquals(Optional.of(equipment), equipmentRepository.findById(id));
    }

    @Test
    void isAuthenticated() {
    }
}