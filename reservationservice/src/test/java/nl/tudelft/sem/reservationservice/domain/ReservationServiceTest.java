package nl.tudelft.sem.reservationservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.tudelft.sem.reservationservice.domain.exceptions.ConflictException;
import nl.tudelft.sem.reservationservice.domain.exceptions.ForbiddenException;
import nl.tudelft.sem.reservationservice.domain.exceptions.NotFoundException;
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

public class ReservationServiceTest {

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

    // Test if reservation can be saved.
    @Test
    public void saveUserTest() {
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        assertEquals(reservation, reservationService.saveReservation(reservation));
        assertNotNull(reservationRepository.findAll());
    }

    // Test if team can be gotten successfully.
    @Test
    public void getTeamByIdTest() {
        TeamDto teamDto = new TeamDto();
        when(restTemplate.getForObject(urlGetTeamById, TeamDto.class)).thenReturn(teamDto);
        assertEquals(teamDto, reservationService.getTeamById(1L));
    }

    // Test if non-existing team is not found.
    @Test
    public void getTeamByIdTestNotFound() {
        TeamDto teamDto = new TeamDto();
        when(restTemplate.getForObject(urlGetTeamById, TeamDto.class)).thenThrow(
            NotFoundException.class);
        assertThrows(NotFoundException.class, () -> reservationService.getTeamById(1L));
    }

    // Test if lesson can be gotten successfully.
    @Test
    public void getLessonByIdTest() {
        LessonDto lessonDto = new LessonDto();
        when(restTemplate.getForObject(urlGetLessonById, LessonDto.class)).thenReturn(lessonDto);
        assertEquals(lessonDto, lessonReservationService.getLessonById(1L));
    }

    // Test if non-existing lesson is not found.
    @Test
    public void getLessonByIdTestNotFound() {
        when(restTemplate.getForObject(urlGetLessonById, LessonDto.class)).thenThrow(
            NotFoundException.class);
        assertThrows(NotFoundException.class, () -> lessonReservationService.getLessonById(1L));
    }

    // Test if an existing lesson can be registered successfully.
    @Test
    public void registerLessonTest() {
        Reservation reservation2 = new Reservation();
        reservation2.setTeamId(1L);
        reservation2.setLessonId(1L);
        reservation2.setType(1);
        LessonDto lessonDto = new LessonDto();
        TeamDto teamDto = new TeamDto();

        when(restTemplate.getForObject(urlGetLessonById, LessonDto.class)).thenReturn(lessonDto);
        when(restTemplate.getForObject(urlGetTeamById, TeamDto.class)).thenReturn(teamDto);
        Reservation reservationReturn = lessonReservationService.registerLesson(1L, 1L);

        assertEquals(reservation2.getTeamId(), reservationReturn.getTeamId());
    }

    // Test when registering a non-existing lesson, not found exception is thrown.
    @Test
    public void registerLessonLessonNotFound() {
        when(restTemplate.getForObject(urlGetLessonById, LessonDto.class)).thenThrow(
            NotFoundException.class);
        assertThrows(NotFoundException.class,
            () -> lessonReservationService.registerLesson(1L, 1L));
    }

    // Test when registering as a non-existing team, not found exception is thrown.
    @Test
    public void registerLessonTeamNotFound() {
        when(restTemplate.getForObject(urlGetTeamById, TeamDto.class)).thenThrow(
            NotFoundException.class);
        assertThrows(NotFoundException.class,
            () -> lessonReservationService.registerLesson(1L, 1L));
    }

    // Test if registering for the equipment is successfully.
    @Test
    public void registerEquipmentTest() {
        Reservation reservation2 = new Reservation();
        reservation2.setTeamId(1L);
        reservation2.setEquipmentId("soccerball_1");
        java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2007-09-23 19:10:10.0");
        reservation2.setTimeStart(timestamp);
        reservation2.setType(2);
        TeamDto teamDto = new TeamDto();

        when(restTemplate.getForObject(urlGetTeamById, TeamDto.class)).thenReturn(teamDto);
        Reservation reservationReturn = reservationService.reserveEquipment(reservation2);

        assertEquals(reservation2.getTeamId(), reservationReturn.getTeamId());
    }

    // Private method to get the beginning of the day of reservation
    private Timestamp getDayBeginning() {
        long timestamp = timeStart.getTime();
        Calendar calendar = Calendar.getInstance();
        // Get start and end time of the day of reservation
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }

    // Test if registering for the equipment is unsuccessfully as the equipment is booked for the
    // time.
    @Test
    public void registerEquipmentConflictTest() {
        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setTeamId(1L);
        reservation2.setEquipmentId("soccerball_1");
        java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2007-09-23 10:10:10.0");
        reservation2.setTimeStart(timestamp);
        reservation2.setType(2);
        TeamDto teamDto = new TeamDto();

        when(restTemplate.getForObject(urlGetTeamById, TeamDto.class)).thenReturn(teamDto);
        Timestamp oneHourPre = new Timestamp(timestamp.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timestamp.getTime() + (1000 * 60 * 60));
        Set<Reservation> set = new HashSet<>();
        set.add(reservation2);

        when(reservationRepository.getReservationsByTimeStartIsWithinAndTypeAndId(oneHourPre,
            oneHourAfter, 2, "soccerball_1")).thenReturn(set);

        assertThrows(ConflictException.class,
            () -> reservationService.reserveEquipment(reservation2));
    }

    // Test if the team that used the equipment last can be gotten.
    @Test
    public void equipmentLastUsage() {
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new Timestamp(calendar.getTimeInMillis());

        List<Reservation> list = new ArrayList<>();
        list.add(reservation);
        when(reservationRepository.getReservationsByTimeStartIsBeforeAndTypeAndId(currentTimestamp,
            2, equipmentId)).thenReturn(list);

        when(restTemplate.getForObject(urlGetTeamById, TeamDto.class)).thenReturn(theTeam);

        assertEquals(theTeam, reservationService.equipmentLastUsage(calendar, equipmentId));
    }

    // Test if the team that used the equipment last cannot be gotten as no one used it.
    @Test
    public void equipmentLastUsage2() {
        Calendar calendar = Calendar.getInstance();
        Timestamp currentTimestamp = new Timestamp(calendar.getTimeInMillis());
        List<Reservation> list = new ArrayList<>();

        when(reservationRepository.getReservationsByTimeStartIsBeforeAndTypeAndId(currentTimestamp,
            2, equipmentId)).thenReturn(list);

        when(restTemplate.getForObject(urlGetTeamById, TeamDto.class)).thenReturn(theTeam);

        assertThrows(NotFoundException.class,
            () -> reservationService.equipmentLastUsage(calendar, equipmentId));
    }

    // Test an unsuccessful getLocationByName
    @Test
    public void getLocationByNameTestNotFound() {
        when(restTemplate.getForObject(urlGetLocationByName, LocationDto.class)).thenThrow(
            NotFoundException.class);
        assertThrows(NotFoundException.class,
            () -> reservationService.getLocationByName(locationName));
    }

    // Test if a location cannot be reserved when the location is already booked in the time period.
    @Test
    public void reserveLocationConflictTest() {
        Timestamp oneHourPre = new Timestamp(timeStart.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timeStart.getTime() + (1000 * 60 * 60));

        Set<Reservation> conflictingReservations = new HashSet<>();
        conflictingReservations.add(
            returnedLocationReservation); // There is some conflicting reservation

        when(reservationRepository.getReservationsByTimeStartIsWithinAndType(oneHourPre,
            oneHourAfter, 1)).thenReturn(conflictingReservations);

        when(reservationService.getTeamById(teamId)).thenReturn(theTeam);
        when(reservationService.getLocationByName(locationName)).thenReturn(locationDto);

        Timestamp dayBeginning = getDayBeginning();
        Timestamp dayEnding = new Timestamp(dayBeginning.getTime() + 24 * 60 * 60 * 1000);
        when(reservationRepository.getReservationsByTimeStartIsWithinAndTeamIdAndType(dayBeginning,
            dayEnding, teamId, 1)).thenReturn(new HashSet<Reservation>());

        when(reservationRepository.save(locationReservationDto)).thenReturn(
            returnedLocationReservation);

        assertThrows(ConflictException.class,
            () -> reservationService.reserveLocation(locationReservationDto, sportName));

    }

    // Test if a location can be reserved successfully.
    @Test
    public void reserveLocationTest() {
        Timestamp oneHourPre = new Timestamp(timeStart.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timeStart.getTime() + (1000 * 60 * 60));

        Timestamp dayBeginning = getDayBeginning();
        Timestamp dayEnding = new Timestamp(dayBeginning.getTime() + 24 * 60 * 60 * 1000);

        Set<Reservation> conflictingReservations = new HashSet<>();

        when(reservationRepository.getReservationsByTimeStartIsWithinAndType(oneHourPre,
            oneHourAfter, 1)).thenReturn(conflictingReservations);

        when(reservationService.getTeamById(teamId)).thenReturn(theTeam);
        when(reservationService.getLocationByName(locationName)).thenReturn(locationDto);
        when(reservationRepository.getReservationsByTimeStartIsWithinAndTeamIdAndType(dayBeginning,
            dayEnding, teamId, 1)).thenReturn(new HashSet<Reservation>());

        when(reservationRepository.save(locationReservationDto)).thenReturn(
            returnedLocationReservation);

        assertEquals(1L,
            reservationService.reserveLocation(locationReservationDto, sportName).getId());
    }

    // Test if a location can be reserved successfully. (for boundary testing)
    // startTime is 2012-04-18 22:00:00
    @Test
    public void reserveLocationTest2() {
        Timestamp startTime = Timestamp.valueOf("2012-04-18 22:00:00");
        locationReservationDto.setTimeStart(startTime);
        Timestamp oneHourPre = new Timestamp(startTime.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(startTime.getTime() + (1000 * 60 * 60));

        Timestamp dayBeginning = getDayBeginning();
        Timestamp dayEnding = new Timestamp(dayBeginning.getTime() + 24 * 60 * 60 * 1000);

        Set<Reservation> conflictingReservations = new HashSet<>();

        when(reservationRepository.getReservationsByTimeStartIsWithinAndType(oneHourPre,
            oneHourAfter, 1)).thenReturn(conflictingReservations);

        when(reservationService.getTeamById(teamId)).thenReturn(theTeam);
        when(reservationService.getLocationByName(locationName)).thenReturn(locationDto);
        when(reservationRepository.getReservationsByTimeStartIsWithinAndTeamIdAndType(dayBeginning,
            dayEnding, teamId, 1)).thenReturn(new HashSet<Reservation>());

        when(reservationRepository.save(locationReservationDto)).thenReturn(
            returnedLocationReservation);

        assertEquals(1L,
            reservationService.reserveLocation(locationReservationDto, sportName).getId());
    }

    // Test if a location cannot be reserved successfully since it does not match the sport
    // wanting to play.
    @Test
    public void reserveLocationTestMismatchedSport() {
        Timestamp oneHourPre = new Timestamp(timeStart.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timeStart.getTime() + (1000 * 60 * 60));

        Timestamp dayBeginning = getDayBeginning();
        Timestamp dayEnding = new Timestamp(dayBeginning.getTime() + 24 * 60 * 60 * 1000);

        Set<Reservation> conflictingReservations = new HashSet<>();

        when(reservationRepository.getReservationsByTimeStartIsWithinAndType(oneHourPre,
            oneHourAfter, 1)).thenReturn(conflictingReservations);

        when(reservationService.getTeamById(teamId)).thenReturn(theTeam);
        when(reservationService.getLocationByName(locationName)).thenReturn(locationDto);
        when(reservationRepository.getReservationsByTimeStartIsWithinAndTeamIdAndType(dayBeginning,
            dayEnding, teamId, 1)).thenReturn(new HashSet<Reservation>());

        when(reservationRepository.save(locationReservationDto)).thenReturn(
            returnedLocationReservation);

        assertThrows(ForbiddenException.class,
            () -> reservationService.reserveLocation(locationReservationDto,
                "football")); // Football here
    }

    // Test if a location cannot be reserved as the team size is smaller than the
    // min number of people require to reserve the location.
    @Test
    public void reserveLocationTestSmallTeamSize() {
        Timestamp oneHourPre = new Timestamp(timeStart.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timeStart.getTime() + (1000 * 60 * 60));

        Timestamp dayBeginning = getDayBeginning();
        Timestamp dayEnding = new Timestamp(dayBeginning.getTime() + 24 * 60 * 60 * 1000);

        Set<Reservation> conflictingReservations = new HashSet<>();
        TeamDto smallTeam = new TeamDto(1L, "Team1", "joinToken", user1.getUsername(),
            new HashSet<UserDto>()); // Team of nobody
        when(reservationRepository.getReservationsByTimeStartIsWithinAndType(oneHourPre,
            oneHourAfter, 1)).thenReturn(conflictingReservations);

        when(reservationService.getTeamById(teamId)).thenReturn(smallTeam);
        when(reservationService.getLocationByName(locationName)).thenReturn(locationDto);
        when(reservationRepository.getReservationsByTimeStartIsWithinAndTeamIdAndType(dayBeginning,
            dayEnding, teamId, 1)).thenReturn(new HashSet<Reservation>());

        when(reservationRepository.save(locationReservationDto)).thenReturn(
            returnedLocationReservation);

        assertThrows(ForbiddenException.class,
            () -> reservationService.reserveLocation(locationReservationDto, "tennis"));
    }

    // Test if a location cannot be reserved as the team size is larger than the capacity of the
    // location.
    @Test
    public void reserveLocationTestLargeTeamSize() {
        Timestamp oneHourPre = new Timestamp(timeStart.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timeStart.getTime() + (1000 * 60 * 60));

        Timestamp dayBeginning = getDayBeginning();
        Timestamp dayEnding = new Timestamp(dayBeginning.getTime() + 24 * 60 * 60 * 1000);
        Set<Reservation> conflictingReservations = new HashSet<>();
        LocationDto zeroMinOneMaxLocation =
            new LocationDto(locationName, 0, 1, sports, LocationType.HALL);
        // min = 0, max = 1

        when(reservationRepository.getReservationsByTimeStartIsWithinAndType(oneHourPre,
            oneHourAfter, 1)).thenReturn(conflictingReservations);

        when(reservationService.getTeamById(teamId)).thenReturn(theTeam);
        when(reservationService.getLocationByName(locationName)).thenReturn(zeroMinOneMaxLocation);
        when(reservationRepository.getReservationsByTimeStartIsWithinAndTeamIdAndType(dayBeginning,
            dayEnding, teamId, 1)).thenReturn(new HashSet<Reservation>());

        when(reservationRepository.save(locationReservationDto)).thenReturn(
            returnedLocationReservation);

        assertThrows(ForbiddenException.class,
            () -> reservationService.reserveLocation(locationReservationDto, "tennis"));
    }

    // Test if a location cannot be reserved as the team size is smaller than
    // the min number of people to play the sport.
    @Test
    public void reserveLocationTestSmallTeamSize2() {
        Timestamp oneHourPre = new Timestamp(timeStart.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timeStart.getTime() + (1000 * 60 * 60));

        Set<Reservation> conflictingReservations = new HashSet<>();

        sportTennis.setMinPlayers(3); // two users < 3

        when(reservationRepository.getReservationsByTimeStartIsWithinAndType(oneHourPre,
            oneHourAfter, 1)).thenReturn(conflictingReservations);

        when(reservationService.getTeamById(teamId)).thenReturn(theTeam);
        when(reservationService.getLocationByName(locationName)).thenReturn(locationDto);

        Timestamp dayBeginning = getDayBeginning();
        Timestamp dayEnding = new Timestamp(dayBeginning.getTime() + 24 * 60 * 60 * 1000);
        when(reservationRepository.getReservationsByTimeStartIsWithinAndTeamIdAndType(dayBeginning,
            dayEnding, teamId, 1)).thenReturn(new HashSet<Reservation>());

        when(reservationRepository.save(locationReservationDto)).thenReturn(
            returnedLocationReservation);

        assertThrows(ForbiddenException.class,
            () -> reservationService.reserveLocation(locationReservationDto, "tennis"));
    }

    // Test if a location cannot be reserved as the team size is larger than
    // the max number of people to play the sport.
    @Test
    public void reserveLocationTestLargeTeamSize2() {
        Timestamp oneHourPre = new Timestamp(timeStart.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timeStart.getTime() + (1000 * 60 * 60));

        Set<Reservation> conflictingReservations = new HashSet<>();

        sportTennis.setMaxPlayers(1); // two users > 1

        when(reservationRepository.getReservationsByTimeStartIsWithinAndType(oneHourPre,
            oneHourAfter, 1)).thenReturn(conflictingReservations);

        when(reservationService.getTeamById(teamId)).thenReturn(theTeam);
        when(reservationService.getLocationByName(locationName)).thenReturn(locationDto);

        Timestamp dayBeginning = getDayBeginning();
        Timestamp dayEnding = new Timestamp(dayBeginning.getTime() + 24 * 60 * 60 * 1000);
        when(reservationRepository.getReservationsByTimeStartIsWithinAndTeamIdAndType(dayBeginning,
            dayEnding, teamId, 1)).thenReturn(new HashSet<Reservation>());

        when(reservationRepository.save(locationReservationDto)).thenReturn(
            returnedLocationReservation);

        assertThrows(ForbiddenException.class,
            () -> reservationService.reserveLocation(locationReservationDto, "tennis"));
    }

    // Test if a location cannot be reserved as a premium user does not have chances to
    // reserve on the day.
    @Test
    public void reserveLocationTestPremiumUserNoTimesToReserve() {
        Timestamp oneHourPre = new Timestamp(timeStart.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timeStart.getTime() + (1000 * 60 * 60));

        Timestamp dayBeginning = getDayBeginning();
        Timestamp dayEnding = new Timestamp(dayBeginning.getTime() + 24 * 60 * 60 * 1000);

        Set<Reservation> conflictingReservations = new HashSet<>();

        when(reservationRepository.getReservationsByTimeStartIsWithinAndType(oneHourPre,
            oneHourAfter, 1)).thenReturn(conflictingReservations);

        Reservation r1 = new Reservation(2L, teamId, locationName, null, null, timeStart, 1);
        Reservation r2 = new Reservation(3L, teamId, locationName, null, null, timeStart, 1);
        Set<Reservation> allReservationsForTheDay =
            new HashSet<>(Set.of(returnedLocationReservation, r1, r2));

        when(reservationService.getTeamById(teamId)).thenReturn(theTeam);
        when(reservationService.getLocationByName(locationName)).thenReturn(locationDto);
        when(reservationRepository.getReservationsByTimeStartIsWithinAndTeamIdAndType(dayBeginning,
            dayEnding, teamId, 1)).thenReturn(allReservationsForTheDay);

        when(reservationRepository.save(locationReservationDto)).thenReturn(
            returnedLocationReservation);

        assertThrows(ForbiddenException.class,
            () -> reservationService.reserveLocation(locationReservationDto, "tennis"));
    }

    // Test if a location cannot be reserved as a basic user does not have chances to
    // reserve on the day.
    @Test
    public void reserveLocationTestBasicUserNoTimesToReserve() {
        Timestamp oneHourPre = new Timestamp(timeStart.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timeStart.getTime() + (1000 * 60 * 60));
        Set<Reservation> conflictingReservations = new HashSet<>();

        when(reservationRepository.getReservationsByTimeStartIsWithinAndType(oneHourPre,
            oneHourAfter, 1)).thenReturn(conflictingReservations);

        when(reservationService.getTeamById(teamId)).thenReturn(theTeam);
        when(reservationService.getLocationByName(locationName)).thenReturn(locationDto);

        Set<Reservation> allReservationsForTheDay =
            new HashSet<>(Set.of(returnedLocationReservation));
        user1.setRole(2); // User 1 is now a basic user
        Timestamp dayBeginning = getDayBeginning();
        Timestamp dayEnding = new Timestamp(dayBeginning.getTime() + 24 * 60 * 60 * 1000);
        when(reservationRepository.getReservationsByTimeStartIsWithinAndTeamIdAndType(dayBeginning,
            dayEnding, teamId, 1)).thenReturn(allReservationsForTheDay);

        when(reservationRepository.save(locationReservationDto)).thenReturn(
            returnedLocationReservation);

        assertThrows(ForbiddenException.class,
            () -> reservationService.reserveLocation(locationReservationDto, "tennis"));
    }

    // Test checkTimeStartIsAfterLessons (boundary testing)
    @Test
    public void checkTimeStartIsAfterLessons() {
        Timestamp startTime = Timestamp.valueOf("2012-04-18 23:00:00");

        assertThrows(ForbiddenException.class,
            () -> reservationService.checkTimeStartIsAfterLessons(startTime));
    }

    // Test checkTimeStartIsAfterLessons (boundary testing)
    @Test
    public void checkTimeStartIsAfterLessons2() {
        Timestamp startTime = Timestamp.valueOf("2012-04-18 15:00:00");

        assertThrows(ForbiddenException.class,
            () -> reservationService.checkTimeStartIsAfterLessons(startTime));
    }

    // Test checkTimeStartIsAfterLessons (boundary testing)
    @Test
    public void checkTimeStartIsAfterLessons3() {
        Timestamp startTime = Timestamp.valueOf("2012-04-18 08:00:00");

        assertThrows(ForbiddenException.class,
            () -> reservationService.checkTimeStartIsAfterLessons(startTime));
    }

}
