package nl.tudelft.sem.lessonservice.entity;

import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * The type Lesson.
 */
@Entity
@Data
@Table(name = "lesson")
public class Lesson {

    @Id
    @Column(name = "lessonId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lessonId;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "timeStart")
    private Timestamp timeStart;

    @Column(name = "timeStop")
    private Timestamp timeStop;

    @Column(name = "hallName")
    private String hallName;

    @Column(name = "sportName")
    private String sportName;

    /**
     * Instantiates a new Lesson.
     */
    public Lesson() {
        //
    }

    /**
     * Instantiates a new Lesson.
     *
     * @param lessonDto the lesson DTO
     */
    public Lesson(LessonDto lessonDto) {
        this.lessonId = lessonDto.getLessonId();
        this.capacity = lessonDto.getCapacity();
        this.timeStart = lessonDto.getTimeStart();
        this.timeStop = lessonDto.getTimeStop();
        this.hallName = lessonDto.getHallName();
        this.sportName = lessonDto.getSportName();
    }

    /**
     * Gets lesson DTO.
     *
     * @return the lesson DTO
     */
    public LessonDto getLessonDto() {
        LessonDto lessonDto =
            new LessonDto(lessonId, capacity, timeStart, timeStop, hallName, sportName);
        return lessonDto;
    }

    /**
     * HashCode method of lesson.
     *
     * @return The hash code of this lesson
     */
    @Override
    public int hashCode() {
        return Objects.hash(lessonId, capacity, timeStart, hallName, sportName);
    }

    /**
     * Equals method for lesson.
     *
     * @param o The lesson object to compare with
     * @return True iff o is equal to this lesson
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lesson lesson = (Lesson) o;
        return capacity == lesson.capacity && lessonId.equals(lesson.lessonId) && timeStart.equals(
            lesson.timeStart) && hallName.equals(lesson.hallName) && sportName.equals(
            lesson.sportName);
    }
}
