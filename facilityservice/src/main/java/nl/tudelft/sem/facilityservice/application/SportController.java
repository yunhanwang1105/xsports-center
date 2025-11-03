package nl.tudelft.sem.facilityservice.application;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.facilityservice.communication.ServiceCommunicator;
import nl.tudelft.sem.facilityservice.domain.Sport;
import nl.tudelft.sem.facilityservice.domain.SportDto;
import nl.tudelft.sem.facilityservice.domain.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sports")
@Slf4j
public class SportController {

    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private ServiceCommunicator serviceCommunicator;

    /**
     * POST: Save a sport.
     * URL: localhost:9191/sports?username=???&password=???
     * BODY:
     * {
     *     "name": STRING,
     *     "minPlayers": INT,
     *     "maxPlayers": INT
     * }
     *
     * @param sportDto The sport to save
     * @param username The username of the admin that wants to save a sport
     * @param password The password of the admin that wants to save a sport
     *
     * @return The SportDto that has been saved
     */
    @PostMapping("/")
    public SportDto saveSport(@RequestBody SportDto sportDto, String username, String password) {
        log.info("Inside as saveSport method of SportController");

        if (!serviceCommunicator.isAuthenticated(username, password)) {
            log.error("Credentials are incorrect");
            return null;
        }
        if (username == null || !serviceCommunicator.isAdmin(username)) {
            log.error("User is not admin.");
            return null;
        }

        if (sportDto == null) {
            log.error("sportDto was NULL");
            return null;
        }

        Sport sport = sportRepository.save(new Sport(sportDto));

        return sport.getSportDto();
    }

    /**
     * GET: Get a sport by its name.
     * URL: localhost:9191/sports/{name}
     *
     * @param name The name of the sport
     *
     * @return The SportDto that has been retrieved
     */
    @GetMapping("/{name}")
    public SportDto findSportByName(@PathVariable("name") String name) {
        log.info("Inside findSportbyName method of SportController");
        if (name == null) {
            log.error("name was NULL");
            return null;
        }

        Optional<Sport> sportOptional = sportRepository.findById(name);
        if (sportOptional.isEmpty()) {
            log.error("Sport named '" + name + "' does not exist");
            return null;
        }

        return sportOptional.get().getSportDto();
    }
}

