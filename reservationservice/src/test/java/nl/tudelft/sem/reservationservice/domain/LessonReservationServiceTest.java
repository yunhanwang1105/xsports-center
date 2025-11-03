package nl.tudelft.sem.reservationservice.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import nl.tudelft.sem.reservationservice.vo.LessonDto;
import nl.tudelft.sem.reservationservice.vo.LocationDto;
import nl.tudelft.sem.reservationservice.vo.LocationType;
import nl.tudelft.sem.reservationservice.vo.SportDto;
import nl.tudelft.sem.reservationservice.vo.TeamDto;
import nl.tudelft.sem.reservationservice.vo.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

class LessonReservationServiceTest {
    @InjectMocks
    private final ReservationService reservationService = new ReservationService();
    @InjectMocks
    private final LessonReservationService lessonReservationService =
        new LessonReservationService();
    Long teamId = 1L;
    String hallName = "X1";
    String equipmentId = "Soccerball";
    Long lessonId = 1L;
    Timestamp timeStart = Timestamp.valueOf("2012-04-18 16:00:00");
    int type = 2;
    Reservation reservation =
        new Reservation(1L, teamId, hallName, equipmentId, lessonId, timeStart, type);
    String urlGetTeamById = "http://localhost:3164/teams/1";
    String urlGetLessonById = "http://localhost:1304/getbyid?lessonId=1";
    String urlGetLocationByName = "http://localhost:2832/location/getbyname/HALL1";
    // The following variables are used to test location reservation.
    String locationName = "HALL1";
    Reservation locationReservationDto =
        new Reservation(null, teamId, locationName, null, null, timeStart, 1);
    Reservation returnedLocationReservation =
        new Reservation(1L, teamId, locationName, null, null, timeStart, 1);
    String sportName = "tennis";
    SportDto sportTennis = new SportDto(sportName, 2, 4);
    Set<SportDto> sports = new HashSet<>(Set.of(sportTennis));
    LocationDto locationDto = new LocationDto(locationName, 1, 10, sports, LocationType.HALL);
    Set<String> teamIds = new HashSet<>(Set.of("1"));
    String password = "123";
    UserDto user1 = new UserDto("han", password, 1, teamIds);
    // user2 is basic subscription
    UserDto user2 = new UserDto("nael", password, 2, teamIds);
    Set<UserDto> usersInTeam = new HashSet<>(Set.of(user1, user2));
    TeamDto theTeam = new TeamDto(1L, "Team1", "joinToken", user1.getUsername(), usersInTeam);
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private RestTemplate restTemplate;

    // Initialize the mock.
    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void registerLessonMutationTesting() {

        Reservation reservation2 = new Reservation();
        reservation2.setTeamId(1L);
        reservation2.setLessonId(1L);
        reservation2.setType(1);
        LessonDto lessonDto = new LessonDto();

        lessonDto.setLessonId(lessonId);
        lessonDto.setHallName(hallName);
        lessonDto.setTimeStart(timeStart);

        TeamDto teamDto = new TeamDto();

        when(restTemplate.getForObject(urlGetLessonById, LessonDto.class)).thenReturn(lessonDto);
        when(restTemplate.getForObject(urlGetTeamById, TeamDto.class)).thenReturn(teamDto);
        Reservation reservationReturn = lessonReservationService.registerLesson(1L, 1L);

        assertEquals(reservation2.getTeamId(), reservationReturn.getTeamId());

        // For mutation testing
        assertEquals(3, reservationReturn.getType());
        assertEquals(hallName, reservationReturn.getHallName());
        assertEquals(lessonId, reservationReturn.getLessonId());
        assertEquals(timeStart, reservationReturn.getTimeStart());

    }
}