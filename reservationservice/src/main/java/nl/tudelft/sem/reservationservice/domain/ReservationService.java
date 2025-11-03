package nl.tudelft.sem.reservationservice.domain;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.tudelft.sem.reservationservice.domain.exceptions.ConflictException;
import nl.tudelft.sem.reservationservice.domain.exceptions.ForbiddenException;
import nl.tudelft.sem.reservationservice.domain.exceptions.NotFoundException;
import nl.tudelft.sem.reservationservice.vo.LocationDto;
import nl.tudelft.sem.reservationservice.vo.SportDto;
import nl.tudelft.sem.reservationservice.vo.TeamDto;
import nl.tudelft.sem.reservationservice.vo.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The Reservation service class.
 */
@Service
public class ReservationService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Save reservation.
     *
     * @param reservation the reservation
     * @return the reservation
     */
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    /**
     * Gets team by id through sending a request.
     *
     * @param teamId the team id
     * @return the team by id
     */
    public TeamDto getTeamById(Long teamId) {
        String urlGetTeamById = "http://localhost:3164/teams/";
        String url = urlGetTeamById + teamId;
        try {
            return restTemplate.getForObject(url, TeamDto.class);
        } catch (Exception e) {
            throw new NotFoundException("Couldn't find team");
        }
    }

    /**
     * Reserve a location (hall/field).
     *
     * @param reservationDto The reservationDto.
     * @param sport          The sport wanting to play.
     * @return The reservation.
     */
    public Reservation reserveLocation(Reservation reservationDto, String sport) {

        Long teamId = reservationDto.getTeamId();
        String locationName = reservationDto.getHallName();
        Timestamp timeStart = reservationDto.getTimeStart();
        TeamDto teamDto = getTeamById(teamId); // Get the teamDto from userservice
        Set<UserDto> members = teamDto.getUsers();

        // Check if there is already some reservation in the time period
        Timestamp oneHourPre = new Timestamp(timeStart.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timeStart.getTime() + (1000 * 60 * 60));

        Set<Reservation> conflictingReservations =
            reservationRepository.getReservationsByTimeStartIsWithinAndType(oneHourPre,
                oneHourAfter, 1);

        if (!conflictingReservations.isEmpty()) {
            throw new ConflictException("There is some conflicting reservation");
        }

        checkTeamSizeAndSport(sport, locationName, members);

        checkAllMemberHaveEnoughReservationTimes(timeStart, members);

        checkTimeStartIsAfterLessons(timeStart);

        return reservationRepository.save(reservationDto);
    }

    /**
     * Check if the sport wanting to play matches the location and
     * if the team size fits the location.
     *
     * @param sport        The sport name
     * @param locationName The location name
     * @param members      The team
     */
    private void checkTeamSizeAndSport(String sport, String locationName, Set<UserDto> members) {
        // Check if the sport wanting to play matches the location
        LocationDto location = getLocationByName(locationName);

        Map<String, SportDto> sportMap = new HashMap<>();
        for (SportDto sportDto : location.getSports()) {
            sportMap.put(sportDto.getName(), sportDto);
        }

        Set<String> sportNames = sportMap.keySet();

        if (!sportNames.contains(sport)) {
            throw new ForbiddenException("The sport you want to play does not match the location");
        }

        int numMemberInTeam = members.size();
        SportDto sportToPlay = sportMap.get(sport);

        // Check if the team size fits the location
        if (numMemberInTeam < location.getMin() || numMemberInTeam > location.getMax()) {
            throw new ForbiddenException("Unacceptable team size");
        }
        // Check if the team size fits the sport
        if (numMemberInTeam < sportToPlay.getMinPlayers()
            || numMemberInTeam > sportToPlay.getMaxPlayers()) {
            throw new ForbiddenException("Unacceptable team size");
        }
    }

    /**
     * Check if each team member is allowed to make reservation on the day with the subscription
     * type.
     *
     * @param timeStart The start time of reservation
     * @param members   The set of users in the team
     */
    private void checkAllMemberHaveEnoughReservationTimes(Timestamp timeStart,
                                                          Set<UserDto> members) {
        // Check if each team member is allowed to make reservation on the day with the
        // subscription type
        // 1 means premium, 2 mean basic
        Timestamp dayBeginning = getDayBeginning(timeStart);
        Timestamp dayEnding = new Timestamp(dayBeginning.getTime() + 24 * 60 * 60 * 1000);

        // Check if all group members have enough reservation times
        for (UserDto member : members) {
            int role = member.getRole();
            Set<Reservation> reservations = new HashSet<>();

            // Find all location reservations of the user on that day
            for (String idOfTeam : member.getTeamIds()) {
                Long idTeam = Long.valueOf(idOfTeam);
                reservations.addAll(
                    reservationRepository.getReservationsByTimeStartIsWithinAndTeamIdAndType(
                        dayBeginning, dayEnding, idTeam, 1));
            }

            if (role == 1 && reservations.size() > 2) {
                throw new ForbiddenException("One premium member of your team has "
                    + "reached the maximum number of reservations for the day");
            } else if (role == 2 && !reservations.isEmpty()) {
                throw new ForbiddenException("One basic member of your team has "
                    + "reached the maximum number of reservations for the day");
            }
        }
    }

    private Timestamp getDayBeginning(Timestamp timeStart) {
        long timestamp = timeStart.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp dayBeginning = new Timestamp(calendar.getTimeInMillis());
        return dayBeginning;
    }

    /**
     * Gets location by its name.
     *
     * @param location The location name
     * @return The locationDto
     */
    public LocationDto getLocationByName(String location) {
        String urlGetLocationByName = "http://localhost:2832/location/getbyname/";
        String url = urlGetLocationByName + location;

        try {
            return restTemplate.getForObject(url, LocationDto.class);
        } catch (Exception e) {
            throw new NotFoundException("Couldn't find lesson");
        }
    }

    /**
     * This method checks if the timeStart is from 16:00 to 23:00 (when no lessons).
     *
     * @param timeStart The start time.
     */
    public void checkTimeStartIsAfterLessons(Timestamp timeStart) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStart.getTime());

        if (calendar.get(Calendar.HOUR_OF_DAY) > 22) {
            throw new ForbiddenException("The start time is after 23:00");
        }

        if (calendar.get(Calendar.HOUR_OF_DAY) < 16) {
            throw new ForbiddenException("The start time is before 16:00");
        }
    }

    /**
     * Reserve an equipment.
     *
     * @param reservationDto The reservationDto.
     * @return The Reservation saved.
     */
    public Reservation reserveEquipment(Reservation reservationDto) {

        String equipmentId = reservationDto.getEquipmentId();
        Timestamp timeStart = reservationDto.getTimeStart();

        // Check if there is already some reservation in the time period
        Timestamp oneHourPre = new Timestamp(timeStart.getTime() - (1000 * 60 * 60));
        Timestamp oneHourAfter = new Timestamp(timeStart.getTime() + (1000 * 60 * 60));

        Set<Reservation> conflictingReservations =
            reservationRepository.getReservationsByTimeStartIsWithinAndTypeAndId(oneHourPre,
                oneHourAfter, 2, equipmentId);

        if (!conflictingReservations.isEmpty()) {
            throw new ConflictException("There is some conflicting reservation");
        }

        checkTimeStartIsAfterLessons(timeStart);

        reservationRepository.save(reservationDto);
        return reservationDto;
    }

    /**
     * Return the team who used the equipment last.
     *
     * @param now         The current time calendar.
     * @param equipmentId The equipment id.
     * @return The TeamDto of the team.
     */
    public TeamDto equipmentLastUsage(Calendar now, String equipmentId) {
        List<Reservation> pastReservations = retrievePastEquipmentReservations(now, equipmentId);

        // Sort past reservations descending by start times.
        pastReservations.sort(
            (o1, o2) -> -Long.compare(o1.getTimeStart().getTime(), o2.getTimeStart().getTime()));

        Long teamId = pastReservations.get(0).getTeamId(); // Get the id of the team who used last

        return getTeamById(teamId);
    }

    /**
     * Retrieve past equipment reservations according to the current time.
     *
     * @param now         The current time calendar.
     * @param equipmentId The equipment id.
     * @return The list of past reservations.
     */
    private List<Reservation> retrievePastEquipmentReservations(Calendar now, String equipmentId) {
        Timestamp currentTimestamp = new Timestamp(now.getTimeInMillis());

        List<Reservation> pastReservations =
            reservationRepository.getReservationsByTimeStartIsBeforeAndTypeAndId(currentTimestamp,
                2, equipmentId);

        if (pastReservations.isEmpty()) {
            throw new NotFoundException("No past usage of this equipment");
        }
        return pastReservations;
    }

}
