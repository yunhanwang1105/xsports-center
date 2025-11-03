package nl.tudelft.sem.reservationservice.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LessonDtoTest {

    Long lessonId;
    int capacity;
    Timestamp timeStart;
    Timestamp timeStop;
    String hallName;
    String sportName;
    LessonDto lesson;

    @BeforeEach
    void setUp() {
        lessonId = 1L;
        capacity = 72;
        timeStart = new Timestamp(System.currentTimeMillis());
        timeStop = new Timestamp(System.currentTimeMillis() + 1);
        hallName = "X";
        sportName = "Y";
        lesson = new LessonDto(lessonId, capacity, timeStart, timeStop, hallName, sportName);
    }

    // Test get lesson id.
    @Test
    void getLessonId() {
        assertEquals(lessonId, lesson.getLessonId());
    }

    // Test set lesson id.
    @Test
    void setLessonId() {
        lesson.setLessonId(2L);
        assertEquals(2L, lesson.getLessonId());
    }

    // Test get lesson capacity.
    @Test
    void getCapacity() {
        assertEquals(capacity, lesson.getCapacity());
    }

    // Test set lesson capacity.
    @Test
    void setCapacity() {
        lesson.setCapacity(21);
        assertEquals(21, lesson.getCapacity());
    }

    // Test get lesson's start time.
    @Test
    void getTimeStart() {
        assertEquals(timeStart, lesson.getTimeStart());
    }

    // Test set lesson's start time.
    @Test
    void setTimeStart() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 2);
        lesson.setTimeStart(timestamp);
        assertEquals(timestamp, lesson.getTimeStart());
    }

    // Test get lesson's stop time.
    @Test
    void getTimeStop() {
        assertEquals(timeStop, lesson.getTimeStop());
    }

    // Test set lesson's stop time.
    @Test
    void setTimeStop() {
        lesson.setTimeStop(new Timestamp(System.currentTimeMillis() + 3));
        assertEquals(new Timestamp(System.currentTimeMillis() + 3), lesson.getTimeStop());
    }

    // Test get lesson's hall.
    @Test
    void getHallName() {
        assertEquals(hallName, lesson.getHallName());
    }

    // Test set lesson's hall.
    @Test
    void setHallName() {
        lesson.setHallName("Hall");
        assertEquals("Hall", lesson.getHallName());
    }

    // Test get lesson's sport name.
    @Test
    void getSportName() {
        assertEquals(sportName, lesson.getSportName());
    }

    // Test set lesson's sport name.
    @Test
    void setSportName() {
        lesson.setSportName("Game");
        assertEquals("Game", lesson.getSportName());
    }

    // Test equals.
    @Test
    void testEquals() {
        LessonDto lesson2 =
            new LessonDto(lessonId, capacity, timeStart, timeStop, hallName, sportName);
        assertEquals(lesson, lesson2);
    }

    // Test not equals.
    @Test
    void testEqualsNot() {
        LessonDto lesson2 = new LessonDto(2L, 2, timeStart, timeStop, hallName, sportName);
        assertNotEquals(lesson, lesson2);
        assertFalse(lesson.equals(null));
        assertFalse(lesson.equals("s"));
    }

    // Test hashCode.
    @Test
    void testHashCode() {
        int code = lesson.hashCode();
        assertEquals(code, lesson.hashCode());
    }

    // Test toString.
    @Test
    void testToString() {
        String s = lesson.toString();
        assertEquals(s, lesson.toString());
    }
}