package nl.tudelft.sem.facilityservice.application;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.facilityservice.communication.ServiceCommunicator;
import nl.tudelft.sem.facilityservice.domain.Builder;
import nl.tudelft.sem.facilityservice.domain.Director;
import nl.tudelft.sem.facilityservice.domain.Location;
import nl.tudelft.sem.facilityservice.domain.LocationBuilder;
import nl.tudelft.sem.facilityservice.domain.LocationDto;
import nl.tudelft.sem.facilityservice.domain.LocationRepository;
import nl.tudelft.sem.facilityservice.domain.LocationType;
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
@RequestMapping("/location")
@Slf4j
public class LocationController {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private SportRepository sportRepository;

    @Autowired
    private ServiceCommunicator serviceCommunicator;

    /**
     * POST: Save a location.
     * Only saves the location if the user is allowed to.
     * URL: localhost:9191/location/save?username=???&password=???
     *
     * @param locationDto The LocationDto object containing the information of a location
     * @param username The username of the admin that wants to save a location
     * @param password The password of the admin that wants to save a location
     *
     * @return The LocationDto that has been stored
     */
    @PostMapping("/save")
    public LocationDto saveLocation(@RequestBody LocationDto locationDto,
                                    String username, String password) {
        log.info("Inside as saveLocation method of LocationController");

        if (!serviceCommunicator.isAuthenticated(username, password)) {
            log.error("Credentials are incorrect");
            return null;
        }
        if (username == null || !serviceCommunicator.isAdmin(username)) {
            log.error("User is not admin.");
            return null;
        }

        if (locationDto == null) {
            log.error("locationDto was NULL");
            return null;
        }
        if (!locationDto.isValid()) {
            log.error("provided locationDto was invalid");
            return null;
        }

        Builder builder = new LocationBuilder();
        Director director = new Director();
        if (locationDto.getLocationType() == LocationType.HALL) {
            director.constructHallLocation(builder, locationDto.getName(), locationDto.getMin(),
                locationDto.getMax());
        } else {
            director.constructFieldLocation(builder, locationDto.getName(), locationDto.getMin(),
                locationDto.getMax());
        }

        Location locationRetrieve = builder.build();
        Location location = locationRepository.save(locationRetrieve);

        return location.getLocationDto();
    }

    /**
     * GET: Add a sport to a location.
     * Only adds the sport if the user is allowed to.
     * URL: localhost:9191/location/addsport?sport=???&location=???&username=???&password=???
     *
     * @param sport The sport to add
     * @param location The location to add the sport to
     * @param username The username of the admin that wants to add a sport to a location
     * @param password The password of the admin that wants to add a sport to a location
     *
     * @return The SportDto that has been added
     */
    @GetMapping("/addsport")
    public SportDto addSport(String sport, String location, String username, String password) {
        log.info("Inside as addSport method of LocationController");

        if (!serviceCommunicator.isAuthenticated(username, password)) {
            log.error("Credentials are incorrect");
            return null;
        }
        if (username == null || !serviceCommunicator.isAdmin(username)) {
            log.error("User is not admin.");
            return null;
        }

        if (sport == null) {
            log.error("sport was NULL");
            return null;
        }
        if (location == null) {
            log.error("location was NULL");
            return null;
        }

        Optional<Location> locationOptional = locationRepository.findById(location);
        if (locationOptional.isEmpty()) {
            log.error("Target location named '" + location + "' does not exist");
            return null;
        }
        Location locationObject = locationOptional.get();

        Optional<Sport> sportOptional = sportRepository.findById(sport);
        if (sportOptional.isEmpty()) {
            log.error("Target sport named '" + sport + "' does not exist");
            return null;
        }
        Sport sportObject = sportOptional.get();

        locationObject.addSport(sportObject);
        locationRepository.save(locationObject);

        return sportObject.getSportDto();
    }

    /**
     * GET: Find a location by providing its name.
     * URL: localhost:9191/location/getbyname?name=???
     *
     * @param name The name of the location to retrieve
     *
     * @return The LocationDto that has been retrieve, or null
     */
    @GetMapping("/getbyname/{name}")
    public LocationDto getLocationByName(@PathVariable("name") String name) {
        log.info("Inside getLocationByName method of LocationController");
        if (name == null) {
            log.error("name was NULL");
            return null;
        }

        Optional<Location> locationOptional = locationRepository.findById(name);
        if (locationOptional.isEmpty()) {
            log.error("Location named '" + name + "' does not exist");
            return null;
        }

        return locationOptional.get().getLocationDto();
    }
}
