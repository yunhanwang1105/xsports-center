package nl.tudelft.sem.reservationservice.vo;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class LessonDto {
    private Long lessonId;
    private int capacity;
    private Timestamp timeStart;
    private Timestamp timeStop;
    private String hallName;
    private String sportName;

    /**
     * Hash code method.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return lessonId.hashCode();
    }

    /**
     * Equals method for this object.
     *
     * @param o The object
     * @return True iff equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LessonDto lessonDto = (LessonDto) o;

        return lessonId.equals(lessonDto.lessonId);
    }

    /**
     * ToString method.
     *
     * @return The toString
     */
    @Override
    public String toString() {
        return "LessonDto{" + "lessonId=" + lessonId + '}';
    }
}
