package nl.tudelft.sem.reservationservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import org.junit.jupiter.api.Test;

public class ReservationTest {

    Long teamId = 1L;
    String hallName = "X1";
    String equipmentId = "Soccerball";
    Long lessonId = 1L;
    Timestamp timeStart = Timestamp.valueOf("2012-04-18 19:00:00");
    int type = 2;
    Reservation reservation =
        new Reservation(1L, teamId, hallName, equipmentId, lessonId, timeStart, type);

    @Test
    public void equalsTest() {
        assertEquals(reservation, reservation);
    }

    @Test
    public void equalsNotTeamIdTest() {
        Reservation reservation2 =
            new Reservation(1L, 2L, hallName, equipmentId, lessonId, timeStart, type);
        assertNotEquals(reservation, reservation2);
    }

    @Test
    public void equalsNotHallTest() {
        Reservation reservation2 =
            new Reservation(1L, teamId, "X2", equipmentId, lessonId, timeStart, type);
        assertNotEquals(reservation, reservation2);
    }

    @Test
    public void equalsNotEquipmentTest() {
        Reservation reservation2 =
            new Reservation(1L, teamId, hallName, "volleyball", lessonId, timeStart, type);
        assertNotEquals(reservation, reservation2);
    }

    @Test
    public void equalsNotLessonIdTest() {
        Reservation reservation2 =
            new Reservation(1L, teamId, hallName, equipmentId, 4L, timeStart, type);
        assertNotEquals(reservation, reservation2);
    }

    @Test
    public void equalsNotTimestampTest() {
        Reservation reservation2 = new Reservation(1L, teamId, hallName, equipmentId, lessonId,
            Timestamp.valueOf("2012-04-18 18:00:00"), type);
        assertNotEquals(reservation, reservation2);
    }

    @Test
    public void equalsNotTypeTest() {
        Reservation reservation2 =
            new Reservation(1L, teamId, hallName, equipmentId, lessonId, timeStart, 4);
        assertNotEquals(reservation, reservation2);
    }

    @Test
    public void testEquals_SymmetricFalse() {
        Reservation reservation2 =
            new Reservation(1L, teamId, hallName, equipmentId, lessonId, timeStart, 4);
        assertFalse(reservation.equals(reservation2) && reservation2.equals(reservation));
        assertNotEquals(reservation.hashCode(), reservation2.hashCode());
    }

    @Test
    public void testEqualsNot() {
        Reservation reservation2 =
            new Reservation(2L, teamId, hallName, equipmentId, lessonId, timeStart, 4);
        assertFalse(reservation.equals(reservation2) && reservation2.equals(reservation));
        assertNotEquals(reservation.hashCode(), reservation2.hashCode());
        assertFalse(reservation.equals(null));
        assertFalse(reservation.equals("s"));
        assertFalse(reservation.equals(reservation2));
    }

    @Test
    public void testEquals_SymmetricTrue() {
        assertEquals(reservation.hashCode(), reservation.hashCode());
    }

    @Test
    public void toStringTest() {
        String toString = reservation.toString();
        assertTrue(toString.contains("Reservation{id=" + 1L));
    }

}
