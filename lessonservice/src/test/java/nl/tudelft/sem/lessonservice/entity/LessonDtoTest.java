package nl.tudelft.sem.lessonservice.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void getLessonId() {
        assertEquals(lessonId, lesson.getLessonId());
    }

    @Test
    void setLessonId() {
        lesson.setLessonId(2L);
        assertEquals(2L, lesson.getLessonId());
    }

    @Test
    void getCapacity() {
        assertEquals(capacity, lesson.getCapacity());
    }

    @Test
    void setCapacity() {
        lesson.setCapacity(21);
        assertEquals(21, lesson.getCapacity());
    }

    @Test
    void getTimeStart() {
        assertEquals(timeStart, lesson.getTimeStart());
    }

    @Test
    void setTimeStart() {
        Timestamp t = new Timestamp(System.currentTimeMillis() + 2);
        lesson.setTimeStart(t);
        assertEquals(t, lesson.getTimeStart());
    }

    @Test
    void getTimeStop() {
        assertEquals(timeStop, lesson.getTimeStop());
    }

    @Test
    void setTimeStop() {
        lesson.setTimeStop(new Timestamp(System.currentTimeMillis() + 3));
        assertEquals(new Timestamp(System.currentTimeMillis() + 3), lesson.getTimeStop());
    }

    @Test
    void getHallName() {
        assertEquals(hallName, lesson.getHallName());
    }

    @Test
    void setHallName() {
        lesson.setHallName("Hall");
        assertEquals("Hall", lesson.getHallName());
    }

    @Test
    void getSportName() {
        assertEquals(sportName, lesson.getSportName());
    }

    @Test
    void setSportName() {
        lesson.setSportName("Game");
        assertEquals("Game", lesson.getSportName());
    }

    @Test
    void testEquals() {
        LessonDto lesson2 =
            new LessonDto(lessonId, capacity, timeStart, timeStop, hallName, sportName);
        assertTrue(lesson.equals(lesson2));
    }

    @Test
    void testEqualsNot() {
        LessonDto lesson2 = new LessonDto(2L, 2, timeStart, timeStop, hallName, sportName);
        assertFalse(lesson.equals(lesson2));
    }

    @Test
    void testHashCode() {
        LessonDto lesson2 =
            new LessonDto(lessonId, capacity, timeStart, timeStop, hallName, sportName);
        assertEquals(lesson.hashCode(), lesson2.hashCode());

    }
}