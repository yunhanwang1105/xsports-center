package nl.tudelft.sem.reservationservice.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Get reservations by their start times and reservation type.
    @Query(value = "SELECT * FROM reservation WHERE time_start BETWEEN ?1 AND ?2 AND type = ?3",
        nativeQuery = true)
    Set<Reservation> getReservationsByTimeStartIsWithinAndType(Timestamp start, Timestamp end,
                                                               int type);

    // Get equipment reservations by start times and equipment id.
    @Query(value = "SELECT * FROM reservation WHERE time_start BETWEEN ?1 AND ?2 AND type = ?3 "
        + "AND equipmentID = ?4", nativeQuery = true)
    Set<Reservation> getReservationsByTimeStartIsWithinAndTypeAndId(Timestamp start, Timestamp end,
                                                                    int type, String equipmentId);

    // Get location reservations by start times and team id.
    @Query(value = "SELECT * FROM reservation WHERE time_start "
        + "BETWEEN ?1 AND ?2 AND team_id = ?3 AND type = ?4", nativeQuery = true)
    Set<Reservation> getReservationsByTimeStartIsWithinAndTeamIdAndType(Timestamp start,
                                                                        Timestamp end, Long teamId,
                                                                        int type);

    // Get reservations of the equipment in which start times are before now.
    @Query(value = "SELECT * FROM reservation WHERE time_start <= ?1 AND type = ?2 AND "
        + "equipmentID = ?3",
        nativeQuery = true)
    List<Reservation> getReservationsByTimeStartIsBeforeAndTypeAndId(Timestamp now, int type,
                                                                     String equipmentId);

}