package nl.tudelft.sem.lessonservice.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LessonTest {
    Long lessonId;
    int capacity;
    Timestamp timeStart;
    Timestamp timeStop;
    String hallName;
    String sportName;
    LessonDto lessonDto;
    Lesson lesson;

    @BeforeEach
    void setUp() {
        lessonId = 1L;
        capacity = 72;
        timeStart = new Timestamp(System.currentTimeMillis());
        timeStop = new Timestamp(System.currentTimeMillis() + 1);
        hallName = "X";
        sportName = "Y";
        lessonDto = new LessonDto(lessonId, capacity, timeStart, timeStop, hallName, sportName);
        lesson = new Lesson(lessonDto);
    }

    @Test
    void getLessonDto() {
        assertEquals(lessonDto, lesson.getLessonDto());
    }

    @Test
    void testEquals() {
        LessonDto lessonDto2 =
            new LessonDto(lessonId, capacity, timeStart, timeStop, hallName, sportName);
        Lesson lesson2 = new Lesson(lessonDto2);
        assertTrue(lesson.equals(lesson2));
    }

    @Test
    void testEqualsNot() {
        LessonDto lessonDto2 =
            new LessonDto(lessonId, 17, timeStart, timeStop, hallName, sportName);
        Lesson lesson2 = new Lesson(lessonDto2);
        assertFalse(lesson.equals(lesson2));
    }

    @Test
    void testEqualsSym() {
        LessonDto lessonDto2 =
            new LessonDto(lessonId, capacity, timeStart, timeStop, hallName, sportName);
        Lesson lesson2 = new Lesson(lessonDto2);
        assertTrue(lesson.equals(lesson2) && lesson2.equals(lesson));
    }
}