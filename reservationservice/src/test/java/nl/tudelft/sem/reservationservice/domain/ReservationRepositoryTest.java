package nl.tudelft.sem.reservationservice.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ReservationRepositoryTest {

    Long id = 1L;
    Long teamId = 1L;
    String hallName = "X1";
    String equipmentId = "Soccerball";
    Long lessonId = 1L;
    Timestamp timeStart = Timestamp.valueOf("2012-04-18 19:00:00");
    int type = 2;
    Reservation reservation =
        new Reservation(1L, teamId, hallName, equipmentId, lessonId, timeStart, type);

    @Autowired
    private ReservationRepository reservationRepository;

    // Test getting an empty reservation by providing with a non-existing id.
    @Test
    public void getReservationNullTest() {
        assertEquals(Optional.empty(), reservationRepository.findById(10L));
    }

    // Test if a reservation is indeed saved.
    @Test
    public void saveEqualReservation() {
        reservationRepository.save(reservation);
        assertThat(reservationRepository.findAll()).hasSize(1);
    }

    // Test getReservationsByTimeStartIsWithinAndType (successful)
    @Test
    public void getReservationsByTimeStartIsWithinAndType() {

        reservationRepository.save(reservation);
        Timestamp begin = Timestamp.valueOf("2012-04-17 19:00:00");
        Timestamp end = Timestamp.valueOf("2012-04-19 19:00:00");

        assertEquals(1, reservationRepository
            .getReservationsByTimeStartIsWithinAndType(begin,
            end, type).size());
    }

    // Test getReservationsByTimeStartIsWithinAndType (empty return)
    @Test
    public void getReservationsByTimeStartIsWithinAndTypeEmpty() {

        Timestamp begin = Timestamp.valueOf("2012-04-17 19:00:00");
        Timestamp end = Timestamp.valueOf("2012-04-19 19:00:00");

        assertEquals(0, reservationRepository
            .getReservationsByTimeStartIsWithinAndType(begin,
                end, type).size());
    }

    @Test
    public void getReservationsByTimeStartIsWithinAndTypeAndId() {

        reservationRepository.save(reservation);
        Timestamp begin = Timestamp.valueOf("2012-04-17 19:00:00");
        Timestamp end = Timestamp.valueOf("2012-04-19 19:00:00");

        assertEquals(1, reservationRepository
            .getReservationsByTimeStartIsWithinAndTypeAndId(begin,
                end, type, equipmentId).size());
    }

    @Test
    public void getReservationsByTimeStartIsWithinAndTypeAndIdEmpty() {

        reservationRepository.save(reservation);
        Timestamp begin = Timestamp.valueOf("2012-04-17 19:00:00");
        Timestamp end = Timestamp.valueOf("2012-04-19 19:00:00");

        assertEquals(0, reservationRepository
            .getReservationsByTimeStartIsWithinAndTypeAndId(begin,
                end, type, "net").size()); // No such equipment
    }

    @Test
    public void getReservationsByTimeStartIsWithinAndTeamIdAndType() {

        reservationRepository.save(reservation);
        Timestamp begin = Timestamp.valueOf("2012-04-17 19:00:00");
        Timestamp end = Timestamp.valueOf("2012-04-19 19:00:00");

        assertEquals(1, reservationRepository
            .getReservationsByTimeStartIsWithinAndTeamIdAndType(begin,
                end, teamId, type).size());
    }

    @Test
    public void getReservationsByTimeStartIsWithinAndTeamIdAndTypeNoSuchTeam() {

        reservationRepository.save(reservation);
        Timestamp begin = Timestamp.valueOf("2012-04-17 19:00:00");
        Timestamp end = Timestamp.valueOf("2012-04-19 19:00:00");

        assertEquals(0, reservationRepository
            .getReservationsByTimeStartIsWithinAndTeamIdAndType(begin,
                end, 2L, type).size()); // No such team reserved
    }

    @Test
    public void getReservationsByTimeStartIsBeforeAndTypeAndId() {
        reservationRepository.save(reservation);
        Timestamp end = Timestamp.valueOf("2012-04-19 19:00:00");

        assertEquals(1, reservationRepository
            .getReservationsByTimeStartIsBeforeAndTypeAndId(end, type, equipmentId).size());
    }

    @Test
    public void getReservationsByTimeStartIsBeforeAndTypeAndIdReservationIsAfter() {
        reservationRepository.save(reservation);
        Timestamp end = Timestamp.valueOf("2012-04-15 19:00:00");

        assertEquals(0, reservationRepository
            .getReservationsByTimeStartIsBeforeAndTypeAndId(end, type, equipmentId).size());
    }

}
