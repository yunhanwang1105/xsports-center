package nl.tudelft.sem.reservationservice.domain;

import java.sql.Timestamp;
import nl.tudelft.sem.reservationservice.domain.exceptions.NotFoundException;
import nl.tudelft.sem.reservationservice.vo.LessonDto;
import nl.tudelft.sem.reservationservice.vo.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The Reservation service class.
 */
@Service
public class LessonReservationService {

    String urlGetLessonById = "http://localhost:1304/getbyid?lessonId=";
    String urlGetTeamById = "http://localhost:3164/teams/";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Gets lesson by its id through sending a request.
     *
     * @param lessonId the lesson id
     * @return the lesson by id
     */
    public LessonDto getLessonById(Long lessonId) {
        String url = urlGetLessonById + lessonId;
        try {
            return restTemplate.getForObject(url, LessonDto.class);
        } catch (Exception e) {
            throw new NotFoundException("Couldn't find lesson");
        }
    }

    /**
     * Register for a lesson.
     *
     * @param lessonId the lesson id
     * @param teamId   the team id
     * @return the reservation
     */
    public Reservation registerLesson(Long lessonId, Long teamId) {
        // get the lesson
        LessonDto lessonDto = getLessonById(lessonId);

        // get the team
        TeamDto teamDto;
        String url = urlGetTeamById + teamId;
        try {
            teamDto = restTemplate.getForObject(url, TeamDto.class);
        } catch (Exception e) {
            throw new NotFoundException("Couldn't find team");
        }
        String hallName = lessonDto.getHallName();
        Timestamp timestamp = lessonDto.getTimeStart();
        int type = 3;

        Reservation reservation = new Reservation();
        reservation.setTeamId(teamId);
        reservation.setLessonId(lessonId);
        reservation.setHallName(hallName);
        reservation.setTimeStart(timestamp);
        reservation.setType(type);

        reservationRepository.save(reservation);
        return reservation;
    }

}
