package nl.tudelft.sem.lessonservice.entity;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Lesson DTO.
 */
public class LessonDto {

    private Long lessonId;
    private int capacity;
    private Timestamp timeStart;
    private Timestamp timeStop;
    private String hallName;
    private String sportName;

    /**
     * Instantiates a new Lesson DTO.
     */
    public LessonDto() {

    }

    /**
     * Instantiates a new Lesson DTO.
     *
     * @param lessonId  the lesson id
     * @param capacity  the capacity
     * @param timeStart the time start
     * @param timeStop  the time stop
     * @param hallName  the hall name
     * @param sportName the sport name
     */
    public LessonDto(Long lessonId, int capacity, Timestamp timeStart, Timestamp timeStop,
                     String hallName, String sportName) {
        this.lessonId = lessonId;
        this.capacity = capacity;
        this.timeStart = timeStart;
        this.timeStop = timeStop;
        this.hallName = hallName;
        this.sportName = sportName;
    }

    /**
     * Gets lesson id.
     *
     * @return the lesson id
     */
    public Long getLessonId() {
        return lessonId;
    }

    /**
     * Sets lesson id.
     *
     * @param lessonId the lesson id
     */
    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets time start.
     *
     * @return the time start
     */
    public Timestamp getTimeStart() {
        return timeStart;
    }

    /**
     * Sets time start.
     *
     * @param timeStart the time start
     */
    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * Gets time stop.
     *
     * @return the time stop
     */
    public Timestamp getTimeStop() {
        return timeStop;
    }

    /**
     * Sets time stop.
     *
     * @param timeStop the time stop
     */
    public void setTimeStop(Timestamp timeStop) {
        this.timeStop = timeStop;
    }

    /**
     * Gets hall name.
     *
     * @return the hall name
     */
    public String getHallName() {
        return hallName;
    }

    /**
     * Sets hall name.
     *
     * @param hallName the hall name
     */
    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    /**
     * Gets sport name.
     *
     * @return the sport name
     */
    public String getSportName() {
        return sportName;
    }

    /**
     * Sets sport name.
     *
     * @param sportName the sport name
     */
    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    /**
     * HashCode method of lesson.
     *
     * @return The hash code of this lesson
     */
    @Override
    public int hashCode() {
        return Objects.hash(lessonId, capacity, timeStart, timeStop, hallName, sportName);
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
        LessonDto lessonDto = (LessonDto) o;
        return lessonId.equals(lessonDto.lessonId);
    }

    /**
     * ToString method of lesson.
     *
     * @return The string representation of this lesson
     */
    @Override
    public String toString() {
        return "LessonDTO{" + "lessonId=" + lessonId + ", capacity=" + capacity + ", timeStart="
            + timeStart + ", timeStop=" + timeStop + ", hallName='" + hallName + '\''
            + ", sportName='" + sportName + '\'' + '}';
    }
}
