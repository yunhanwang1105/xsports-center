package nl.tudelft.sem.reservationservice.application;

import java.util.Calendar;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.reservationservice.communication.ServiceCommunication;
import nl.tudelft.sem.reservationservice.domain.LessonReservationService;
import nl.tudelft.sem.reservationservice.domain.Reservation;
import nl.tudelft.sem.reservationservice.domain.ReservationRepository;
import nl.tudelft.sem.reservationservice.domain.ReservationService;
import nl.tudelft.sem.reservationservice.domain.exceptions.NotFoundException;
import nl.tudelft.sem.reservationservice.vo.LessonDto;
import nl.tudelft.sem.reservationservice.vo.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Reservation controller.
 */
@RequestMapping("/reservations")
@RestController
@Slf4j
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private LessonReservationService lessonReservationService;

    /**
     * POST: Save a reservation.
     * URL: localhost:9191/reservations/save?username=???&password=???
     *
     * @param reservationDto reservation object in json format
     * @param username       username of the user that wants to make the reservation
     * @param password       password of the user that wants to make the reservation
     * @return the saved reservation in json format
     */
    @PostMapping("save")
    public Reservation saveReservation(@RequestBody Reservation reservationDto,
                                       String username,
                                       String password) {
        log.info("Inside saveReservation method of ReservationController");

        if (!ServiceCommunication.isAuthenticated(username, password)) {
            log.error("Credentials are incorrect");
            return null;
        }

        if (username == null || reservationDto.getTeamId() == null
            || !ServiceCommunication.isInTeam(username, reservationDto.getTeamId())) {
            log.error("User is not in the team.");
            return null;
        }

        return reservationRepository.save(reservationDto);
    }

    /**
     * GET: Retrieve a reservation by its ID if it exists.
     * URL: localhost:9191/reservations/getbyid?reservationId=???
     *
     * @param reservationId reservation ID
     * @return the retrieved reservation in json format, or null if reservation does not exist
     */
    @GetMapping("getbyid")
    public Reservation findReservationById(Long reservationId) {
        log.info("Inside findReservationById method of ReservationController");
        if (reservationId == null) {
            log.error("reservationId was NULL");
            return null;
        }

        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            log.error("Reservation with reservationId '" + reservationId + "' does not exist");
            return null;
        }

        return reservationOptional.get();
    }

    /**
     * POST: Registers for a hall/location.
     * URL: localhost:9191/reservations/reserveLocation?sport=%sport%&username=%username
     * %&password=%password%
     *
     * @param reservationDto The reservation
     * @param sport          The sport name
     * @param username       The username
     * @param password       The password
     * @return The reservation
     */
    @PostMapping("/reserveLocation")
    public Reservation reserveLocation(@RequestBody Reservation reservationDto, String sport,
                                       String username, String password) {
        log.info("Inside reserveLocation of ReservationController");

        if (!ServiceCommunication.isAuthenticated(username, password)) {
            log.error("Credentials are incorrect");
            return null;
        }

        Long teamId = reservationDto.getTeamId();
        if (username == null || teamId == null || !ServiceCommunication.isInTeam(username,
            teamId)) {
            log.error("User is not in the team.");
            return null;
        }

        return reservationService.reserveLocation(reservationDto, sport);
    }

    /**
     * POST: Reserve equipment.
     * URL: localhost:9191/reservations/reserveEquipment?username=%username%&password=%password%
     *
     * @param reservationDto The reservationDto
     * @param username       The username
     * @param password       The password
     * @return The reservation saved
     */
    @PostMapping("/reserveEquipment")
    public Reservation reserveEquipment(@RequestBody Reservation reservationDto, String username,
                                        String password) {
        log.info("Inside reserveEquipment of ReservationController");

        if (!ServiceCommunication.isAuthenticated(username, password)) {
            log.error("Credentials are incorrect");
            return null;
        }

        Long teamId = reservationDto.getTeamId();
        if (username == null || teamId == null || !ServiceCommunication.isInTeam(username,
            teamId)) {
            log.error("User is not in the team.");
            return null;
        }

        return reservationService.reserveEquipment(reservationDto);
    }

    /**
     * Get: get the team who used the equipment last.
     * URL: localhost:9191/reservations/equipmentLastUsage/{equipmentId}?username=???&password=???
     *
     * @param equipmentId The equipment id.
     * @param username    The username.
     * @param password    The password.
     * @return The TeamDto who used last.
     */
    @GetMapping("/equipmentLastUsage/{equipmentId}")
    public TeamDto equipmentLastUsage(@PathVariable("equipmentId") String equipmentId,
                                      String username, String password) {
        log.info("Inside equipmentLastUsage of ReservationController");

        return reservationService.equipmentLastUsage(Calendar.getInstance(), equipmentId);
    }

    /**
     * POST: Register for a lesson.
     * URL: localhost:9191/reservations/registerLesson/
     * {lessonId}/{teamId}?username=???&password=???
     *
     * @param lessonId the lesson id
     * @param teamId   the team id
     * @param username username of the user that wants to make the reservation
     * @param password password of the user that wants to make the reservation
     * @return the reservation
     * @throws NotFoundException if lesson or team cannot be found (404)
     */
    @PostMapping("/registerLesson/{lessonId}/{teamId}")
    public Reservation registerLesson(@PathVariable("lessonId") Long lessonId,
                                      @PathVariable("teamId") Long teamId, String username,
                                      String password) {
        log.info("Inside registerLesson of ReservationController");

        if (!ServiceCommunication.isAuthenticated(username, password)) {
            log.error("Credentials are incorrect");
            return null;
        }

        if (username == null || teamId == null || !ServiceCommunication.isInTeam(username,
            teamId)) {
            log.error("User is not in the team.");
            return null;
        }

        return lessonReservationService.registerLesson(lessonId, teamId);
    }

}