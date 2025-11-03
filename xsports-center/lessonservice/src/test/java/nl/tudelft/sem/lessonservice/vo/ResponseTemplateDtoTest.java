package nl.tudelft.sem.lessonservice.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import nl.tudelft.sem.lessonservice.entity.LessonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResponseTemplateDtoTest {
    Long lessonId;
    int capacity;
    Timestamp timeStart;
    Timestamp timeStop;
    String hallName;
    String sport;
    LessonDto lesson;
    ResponseTemplateDto responseTemplateDto;
    private LocationDto locationDto;
    private String name;
    private int min;
    private int max;
    private Set<SportDto> sports;
    private String sportName;
    private int minPlayers;
    private int maxPlayers;
    private SportDto sportDto;

    @BeforeEach
    void setUp() {
        name = "pool";
        min = 1;
        max = 50;
        sports = new HashSet<>();
        locationDto = new LocationDto(name, min, max, sports);
        sportName = "Rugby";
        minPlayers = 8;
        maxPlayers = 16;
        sportDto = new SportDto(name, minPlayers, maxPlayers);
        lessonId = 1L;
        capacity = 72;
        timeStart = new Timestamp(System.currentTimeMillis());
        timeStop = new Timestamp(System.currentTimeMillis() + 1);
        hallName = "X";
        sport = "Y";
        lesson = new LessonDto(lessonId, capacity, timeStart, timeStop, hallName, sportName);
        responseTemplateDto = new ResponseTemplateDto(lesson, locationDto, sportDto);
    }

    @Test
    void getLesson() {
        assertEquals(lesson, responseTemplateDto.getLesson());
    }

    @Test
    void getLocation() {
        assertEquals(locationDto, responseTemplateDto.getLocation());
    }

    @Test
    void getSport() {
        assertEquals(sportDto, responseTemplateDto.getSport());
    }

    @Test
    void setSport() {
        SportDto sportDto1 = new SportDto("swimming", minPlayers, maxPlayers);
        responseTemplateDto.setSport(sportDto1);
        assertEquals(sportDto1, responseTemplateDto.getSport());
    }

    @Test
    void setLocation() {
        LocationDto locationDto1 = new LocationDto(name, 0, max, sports);
        responseTemplateDto.setLocation(locationDto1);
        assertEquals(locationDto1, responseTemplateDto.getLocation());
    }

    @Test
    void setLesson() {
        LessonDto lessonDto2 =
            new LessonDto(lessonId, capacity, timeStart, timeStop, "hallName", sportName);
        responseTemplateDto.setLesson(lessonDto2);
        assertEquals(lessonDto2, responseTemplateDto.getLesson());
    }

    @Test
    void testEquals() {
        ResponseTemplateDto responseTemplateDto2 =
            new ResponseTemplateDto(lesson, locationDto, sportDto);
        assertEquals(responseTemplateDto2, responseTemplateDto);
        LessonDto lessonDto2 =
            new LessonDto(4L, capacity, timeStart, timeStop, "hallName", sportName);
        ResponseTemplateDto responseTemplateDto3 =
            new ResponseTemplateDto(lessonDto2, locationDto, sportDto);
        SportDto sportDto2 = new SportDto("swimming", minPlayers, maxPlayers);
        ResponseTemplateDto responseTemplateDto4 =
            new ResponseTemplateDto(lesson, locationDto, sportDto2);
        assertNotEquals(responseTemplateDto, responseTemplateDto4);
        LocationDto locationDto2 = new LocationDto(name, 0, max, sports);
        ResponseTemplateDto responseTemplateDto5 =
            new ResponseTemplateDto(lesson, locationDto2, sportDto);
        assertNotEquals(responseTemplateDto, responseTemplateDto5);
    }

    @Test
    void testHashCode() {
        ResponseTemplateDto responseTemplateDto2 =
            new ResponseTemplateDto(lesson, locationDto, sportDto);
        assertEquals(responseTemplateDto2.hashCode(), responseTemplateDto.hashCode());
    }
}