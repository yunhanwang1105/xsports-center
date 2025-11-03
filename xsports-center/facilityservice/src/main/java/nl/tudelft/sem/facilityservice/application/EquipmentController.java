package nl.tudelft.sem.facilityservice.application;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.facilityservice.communication.ServiceCommunicator;
import nl.tudelft.sem.facilityservice.domain.Equipment;
import nl.tudelft.sem.facilityservice.domain.EquipmentDto;
import nl.tudelft.sem.facilityservice.domain.EquipmentRepository;
import nl.tudelft.sem.facilityservice.domain.LocationRepository;
import nl.tudelft.sem.facilityservice.domain.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/equipment")
@Slf4j
public class EquipmentController {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ServiceCommunicator serviceCommunicator;

    /**
     * POST: Save an equipment.
     * URL: localhost:9191/equipment/saveEquipment/?username=???&password=???
     *
     * @param equipmentDto The equipment to save
     * @param username The username of the admin that wants to save an equipment
     * @param password The password of the admin that wants to save an equipment
     *
     * @return The EquipmentDto that has been saved
     */
    @PostMapping("/saveEquipment")
    public EquipmentDto saveEquipment(@RequestBody EquipmentDto equipmentDto,
                                      String username, String password) {
        log.info("Inside as saveEquipment method of EquipmentController");

        if (!serviceCommunicator.isAuthenticated(username, password)) {
            log.error("Credentials are incorrect");
            return null;
        }
        if (username == null || !serviceCommunicator.isAdmin(username)) {
            log.error("User is not admin.");
            return null;
        }

        if (equipmentDto == null) {
            log.error("equipmentDto was NULL");
            return null;
        }

        return equipmentRepository.save(new Equipment(equipmentDto)).getEquipmentDto();

        // return equipmentDto;
    }

    /**
     * GET: Get a equipment by its ID.
     * URL: localhost:9191/equipment/{equipmentId}
     *
     * @param equipmentId The ID of the equipment
     *
     * @return The EquipmentDto that has been retrieved
     */
    @GetMapping("/{equipment_id}")
    public EquipmentDto getEquipmentByName(@PathVariable("equipment_id") String equipmentId) {
        log.info("Inside getLocationByName method of LocationController");

        if (equipmentId == null) {
            log.error("equipmentId was NULL");
            return null;
        }

        Optional<Equipment> equipmentOptional = equipmentRepository.findById(equipmentId);

        if (equipmentOptional.isEmpty()) {
            log.error("Equipment with id '" + equipmentId + "' does not exist");
            return null;
        }

        return equipmentOptional.get().getEquipmentDto();
    }
}
