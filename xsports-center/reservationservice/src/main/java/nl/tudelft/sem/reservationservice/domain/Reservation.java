package nl.tudelft.sem.reservationservice.domain;

import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "teamId", nullable = false)
    private Long teamId;

    @Column(name = "hallName")
    private String hallName;

    @Column(name = "equipmentID")
    private String equipmentId;

    @Column(name = "lessonID")
    private Long lessonId;

    @Column(name = "timeStart", nullable = false)
    private Timestamp timeStart;

    @Column(name = "type", nullable = false)
    private int type;

    /**
     * Hash code method for reservation.
     *
     * @return The hash code of this reservation
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + teamId.hashCode();
        result = 31 * result + (hallName != null ? hallName.hashCode() : 0);
        result = 31 * result + (equipmentId != null ? equipmentId.hashCode() : 0);
        result = 31 * result + (lessonId != null ? lessonId.hashCode() : 0);
        result = 31 * result + timeStart.hashCode();
        result = 31 * result + type;
        return result;
    }

    /**
     * Equals method for reservation.
     *
     * @param o The user object to compare with
     * @return True iff o is equal to this user
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Reservation that = (Reservation) o;

        if (type != that.type) {
            return false;
        }
        if (!id.equals(that.id)) {
            return false;
        }
        if (!teamId.equals(that.teamId)) {
            return false;
        }
        if (!Objects.equals(hallName, that.hallName)) {
            return false;
        }
        if (!Objects.equals(equipmentId, that.equipmentId)) {
            return false;
        }
        if (!Objects.equals(lessonId, that.lessonId)) {
            return false;
        }
        return timeStart.equals(that.timeStart);
    }

    /**
     * To string method for reservation.
     *
     * @return The string representation of this reservation
     */
    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", teamId=" + teamId + ", hallName='" + hallName + '\''
            + ", equipmentID='" + equipmentId + '\'' + ", lessonID='" + lessonId + '\''
            + ", timeStart=" + timeStart + ", type=" + type + '}';
    }
}
