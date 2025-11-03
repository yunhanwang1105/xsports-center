package nl.tudelft.sem.lessonservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import nl.tudelft.sem.lessonservice.entity.Lesson;
import nl.tudelft.sem.lessonservice.entity.LessonDto;
import nl.tudelft.sem.lessonservice.repository.LessonRepository;
import nl.tudelft.sem.lessonservice.vo.LocationDto;
import nl.tudelft.sem.lessonservice.vo.SportDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

class LessonControllerTest {

    private final transient String lessonId = "Basketball Class";
    Timestamp timeStart = new Timestamp(2022, 01, 9, 11, 47, 23, 1);
    Timestamp timeStop = new Timestamp(2022, 01, 9, 12, 47, 23, 1);
    Lesson lesson =
        new Lesson(new LessonDto(12L, 40, timeStart, timeStop, "BasketballCourt", "Basketball"));
    SportDto sportDto = new SportDto("Baseball", 2, 18);
    private transient LessonController lessonController;
    @Autowired
    @MockBean
    private transient LessonRepository lessonRepository;
    @Autowired
    @MockBean
    private transient RestTemplate restTemplate;
    private Set<SportDto> sports;
    LocationDto locationDto = new LocationDto("BasketballCourt", 2, 20, sports);

    @BeforeEach
    void setUp() {
        this.lessonRepository = mock(LessonRepository.class);
        this.restTemplate = mock(RestTemplate.class);

        lessonController = new LessonController();
        lessonController.setLessonRepository(lessonRepository);
        lessonController.setRestTemplate(restTemplate);
    }

    @Test
    void getHall() {
        lessonController = new LessonController();
        lessonController.setLessonRepository(lessonRepository);
        lessonController.setRestTemplate(restTemplate);
        when(restTemplate.getForObject(
            "http://localhost:2832/location/getbyname/BasketballCourt",
            LocationDto.class)).thenReturn(locationDto);
        LocationDto locationDto2 = lessonController.getHall("BasketballCourt");

        assertEquals(locationDto, locationDto2);
    }

    @Test
    void getSport() {
        lessonController = new LessonController();
        lessonController.setLessonRepository(lessonRepository);
        lessonController.setRestTemplate(restTemplate);
        when(restTemplate.getForObject("http://localhost:2832/sports/Baseball",
            SportDto.class)).thenReturn(sportDto);
        SportDto sportDto2 = lessonController.getSport("Baseball");

        assertEquals(sportDto, sportDto2);
    }

    @Test
    void getMinutes() {
        Timestamp timestamp = new Timestamp(2022, 01, 9, 11, 47, 23, 1);
        assertEquals(47, LessonController.getMinutes(timestamp));
    }

    @Test
    void testSaveLesson() {

    }

    @Test
    void testFindLessonById() {
        lessonController = new LessonController();
        lessonController.setLessonRepository(lessonRepository);
        lessonController.setRestTemplate(restTemplate);
        when(lessonRepository.findById(12L)).thenReturn(Optional.ofNullable(lesson));
        LessonDto lessonDto = lessonController.findLessonById(12L);
        assertEquals(lesson.getLessonDto(), lessonDto);
    }

    @Test
    void testFindLessonBySportName() {
        lessonController = new LessonController();
        lessonController.setLessonRepository(lessonRepository);
        lessonController.setRestTemplate(restTemplate);
        List<Lesson> lessons = new ArrayList<>();
        List<LessonDto> lessons3 = new ArrayList<>();
        lessons.add(lesson);
        when(lessonRepository.findBySportName("FootBall")).thenReturn(lessons);
        List<LessonDto> lessons2 = lessonController.findLessonBySportName("FootBall");
        for (int i = 0; i < lessons.size(); i++) {
            lessons3.add(lessons.get(i).getLessonDto());
        }
        assertEquals(lessons2, lessons3);
    }

    @Test
    void replaceCapacity() {
    }

    @Test
    void changeHall() {
    }

    @Test
    void changeSport() {
    }

    @Test
    void changeTime() {
    }

    @Test
    void deleteLesson() {
    }

    @Test
    void testGetLessonWithAllInformation() {
    }

    @Test
    void testGetHall() {
    }

    @Test
    void testGetSport() {
    }
}

