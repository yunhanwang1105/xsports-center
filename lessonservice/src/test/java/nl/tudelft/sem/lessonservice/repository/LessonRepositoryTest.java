package nl.tudelft.sem.lessonservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.lessonservice.entity.Lesson;
import nl.tudelft.sem.lessonservice.entity.LessonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LessonRepositoryTest {

    Long lessonId;
    int capacity;
    Timestamp timeStart;
    Timestamp timeStop;
    String hallName;
    String sportName;
    LessonDto lessonDto;
    Lesson lesson;
    List<Lesson> lessons;
    @Autowired
    private LessonRepository lessonRepository;

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
        lessons = new ArrayList<>();
        lessons.add(lesson);
    }

    @Test
    public void getLessonNullTest() {
        assertEquals(Optional.empty(), lessonRepository.findById(lessonId));
    }

    @Test
    public void saveLessonTest() {
        assertEquals(lesson, lessonRepository.save(lesson));
        assertNotNull(lessonRepository.findAll());
    }
}