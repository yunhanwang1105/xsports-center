package nl.tudelft.sem.lessonservice.controller;

import static nl.tudelft.sem.lessonservice.communication.ServiceCommunication.isAdmin;
import static nl.tudelft.sem.lessonservice.communication.ServiceCommunication.isAuthenticated;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.lessonservice.entity.Lesson;
import nl.tudelft.sem.lessonservice.entity.LessonDto;
import nl.tudelft.sem.lessonservice.exceptions.NotAcceptable;
import nl.tudelft.sem.lessonservice.exceptions.NotFound;
import nl.tudelft.sem.lessonservice.repository.LessonRepository;
import nl.tudelft.sem.lessonservice.vo.LocationDto;
import nl.tudelft.sem.lessonservice.vo.ResponseTemplateDto;
import nl.tudelft.sem.lessonservice.vo.SportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * The type Lesson controller.
 */
@RestController
@RequestMapping("/lessons")
@Slf4j
public class LessonController {

    /**
     * The Endpoint sport.
     */
    String endpointSport = "http://localhost:2832/sports/";
    /**
     * The Endpoint location.
     */
    String endpointLocation = "http://localhost:2832/location/getbyname/";
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LessonRepository lessonRepository;

    /**
     * Gets hour.
     *
     * @param timestamp the timestamp
     * @return the hour
     */
    public static int getHour(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = null;
        try {
            date = sdf.parse(timestamp.toString());
        } catch (ParseException ex) {
            System.exit(-1);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Gets minutes.
     *
     * @param timestamp the timestamp
     * @return the minutes
     */
    public static int getMinutes(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = null;
        try {
            date = sdf.parse(timestamp.toString());
        } catch (ParseException ex) {
            System.exit(-1);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    /**
     * Checks time.
     *
     * @param lessonDto the lesson dto
     */
    public static void checkTime(LessonDto lessonDto) {
        Timestamp timeStart = lessonDto.getTimeStart();
        int hour = getHour(timeStart);
        int minutes = getMinutes(timeStart);
        if (hour == 15) {
            if (minutes != 0) {
                //log.error("The hour of the reservation is invalid. Choose another hour.");
                throw new NotAcceptable(
                    "The hour of the reservation is invalid. Choose and hour between 09:00 - "
                        + "15:00.");
            }

        }

        if (hour < 9 || hour > 15) {
            //log.error("The hour of the reservation is invalid. Choose another hour.");
            throw new NotAcceptable(
                "The hour of the reservation is invalid. Choose and hour between 09:00 - 15:00.");
        }
    }

    /**
     * Checks authentication.
     *
     * @param username the username
     * @param password the password
     */
    public static void checkAuthentication(String username, String password) {
        if (!isAuthenticated(username, password)) {
            //log.error("Credentials are incorrect");
            throw new NotAcceptable("Credentials are incorrect");
        }
        if (username == null || !isAdmin(username)) {
            //log.error("User is not admin");
            throw new NotAcceptable("User is not admin");
        }

    }

    /**
     * POST: Save a lesson.
     * URL: localhost:1304/lessons/save?username=???&password=???
     *
     * @param lessonDto the lesson to save
     * @param username  username of the admin that wants to save the lesson
     * @param password  password of the admin that wants to save the lesson
     * @return the saved lesson
     */
    @PostMapping("save")
    public LessonDto saveLesson(@RequestBody LessonDto lessonDto, String username,
                                String password) {
        //log.info("Inside saveLesson method of LessonController");

        if (lessonDto == null) {
            //log.error("lessonDto was NULL");
            throw new NotAcceptable("Lesson was NULL");
        }

        checkAuthentication(username, password);
        checkTime(lessonDto);

        Lesson lesson = lessonRepository.save(new Lesson(lessonDto));

        return lesson.getLessonDto();
    }

    /**
     * GET: get a lesson by its ID.
     * URL: localhost:1304/getbyid?lessonId=???
     *
     * @param lessonId the ID of the lesson to retrieve
     * @return the requested lesson or null if it does not exist
     */
    @GetMapping("getbyid")
    public LessonDto findLessonById(Long lessonId) {
        log.info("Inside findLessonById method of LessonController");
        if (lessonId == null) {
            log.error("lessonId was NULL");
            throw new NotAcceptable("Lesson was NULL");
        }

        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (lessonOptional.isEmpty()) {
            log.error("Lesson with lessonId '" + lessonId + "' does not exist");
            throw new NotFound("Couldn't find lesson.");
        }

        return lessonOptional.get().getLessonDto();
    }

    /**
     * GET: Get all lessons with a specific sport.
     * URL: localhost:1304/getbysport?lessonId=???
     *
     * @param sportName the ID of the lesson to retrieve
     * @return a list with the requesteds lessons
     */
    @GetMapping("getbysport")
    public List<LessonDto> findLessonBySportName(String sportName) {
        log.info("Inside findLessonBySportName method of LessonController");
        if (sportName == null) {
            log.error("sportName was NULL");
            throw new NotAcceptable("Sport name was NULL");
        }

        List<Lesson> lessons = lessonRepository.findBySportName(sportName);
        if (lessons == null) {
            return new ArrayList<LessonDto>();
        }

        List<LessonDto> lessonDtos = new LinkedList<>();
        for (Lesson l : lessons) {
            lessonDtos.add(l.getLessonDto());
        }

        return lessonDtos;
    }

    /**
     * Replace capacity of lesson.
     * URL: localhost:1304/lessons/changecapacity/{id}
     *
     * @param capacity the capacity
     * @param id       the id
     * @return the lesson DTO
     */
    // choose option raw, JSON in Postman and type only the capacity
    @PutMapping("/changecapacity/{id}")
    public LessonDto replaceCapacity(@RequestBody int capacity, @PathVariable Long id) {

        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if (lessonOptional.isEmpty()) {
            log.error("Lesson with lessonId '" + id + "' does not exist");
            throw new NotFound("Couldn't find lesson.");
        } else {
            lessonOptional.map(lesson -> {
                lesson.setCapacity(capacity);
                return lessonRepository.save(lesson);
            });
        }
        return null;
    }

    /**
     * Change hall of lesson.
     * URL: localhost:1304/lessons/changehall/{id}
     *
     * @param hallName the hall name
     * @param id       the id
     * @return the lesson DTO
     */
    // choose option raw, Text in Postman and type only the changed hall name
    @PutMapping("/changehall/{id}")
    public LessonDto changeHall(@RequestBody String hallName, @PathVariable Long id) {

        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if (lessonOptional.isEmpty()) {
            log.error("Lesson with lessonId '" + id + "' does not exist");
            throw new NotFound("Couldn't find lesson.");
        } else {
            lessonOptional.map(lesson -> {
                lesson.setHallName(hallName);
                return lessonRepository.save(lesson);
            });
        }
        return null;
    }

    /**
     * Change sport of lesson.
     * URL: localhost:1304/lessons/changesport/{id}
     *
     * @param sportName the sport name
     * @param id        the id
     * @return the lesson DTO
     */
    // choose option raw, Text in Postman and type only the changed sport name
    @PutMapping("/changesport/{id}")
    public LessonDto changeSport(@RequestBody String sportName, @PathVariable Long id) {

        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if (lessonOptional.isEmpty()) {
            log.error("Lesson with lessonId '" + id + "' does not exist");
            throw new NotFound("Couldn't find lesson.");
        } else {
            lessonOptional.map(lesson -> {
                lesson.setSportName(sportName);
                return lessonRepository.save(lesson);
            });
        }
        return null;
    }

    /**
     * Change time of lesson.
     * URL: localhost:1304/lessons/changetime/{id}
     *
     * @param newTime the new time
     * @param id      the id
     * @return the lesson DTO
     */
    // choose option raw, JSON in Postman and type the timestamp, e.g. "2022-01-07T18:20:31.995"
    @PutMapping("/changetime/{id}")
    public LessonDto changeTime(@RequestBody Timestamp newTime, @PathVariable Long id) {

        //the system works on UTC time so the string passed will contain the time given by the 
        // getbyid endpoint
        //however, if the machine is set to another timezone, in the database there is an offset
        //e.g. I pass "2022-01-07T09:50:31.995"
        //when I run getbyid I get "2022-01-07T09:50:31.995+00:00"
        //but on the h2console I see 2022-01-07 10:20:31.995, that is because my machine runs on 
        // UTC+1

        int hour = getHour(newTime);
        int minutes = getMinutes(newTime);
        if (hour == 15) {
            if (minutes != 0) {
                //log.error("The hour of the reservation is invalid. Choose another hour.");
                throw new NotAcceptable(
                    "The hour of the reservation is invalid. Choose and hour between 09:00 - "
                        + "15:00.");
            }

        }

        if (hour < 9 || hour > 15) {
            log.error("The hour of the reservation is invalid. Choose another hour.");
            throw new NotAcceptable(
                "The hour of the reservation is invalid. Choose and hour between 09:00 - 15:00.");
        }

        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if (lessonOptional.isEmpty()) {
            log.error("Lesson with lessonId '" + id + "' does not exist");
            throw new NotFound("Couldn't find lesson.");
        } else {
            lessonOptional.map(lesson -> {
                lesson.setTimeStart(newTime);
                lesson.setTimeStop(Timestamp.from(newTime.toInstant().plus(1, ChronoUnit.HOURS)));
                return lessonRepository.save(lesson);
            });
        }
        return null;
    }

    /**
     * Delete lesson.
     * URL: localhost:1304/lessons/delete/{id}
     *
     * @param lessonId the lesson id
     * @return the map
     */
    @DeleteMapping("delete/{id}")
    public Map<String, Boolean> deleteLesson(@PathVariable("id") Long lessonId) {
        log.info("Inside deleteLesson method of LessonController");

        if (lessonId == null) {
            log.error("lessonId was NULL");
            throw new NotFound("Lesson was NULL");
        }

        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (lessonOptional.isEmpty()) {
            log.error("Lesson with lessonId '" + lessonId + "' does not exist");
            throw new NotFound("Couldn't find lesson.");
        }

        lessonRepository.delete(lessonOptional.get());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    /**
     * Gets lesson with all information.
     * URL: localhost:1304/lessons/getAllInformation/{id}
     *
     * @param lessonId the lesson id
     * @return the lesson with all information
     */
    @GetMapping("/getAllInformation/{id}")
    public ResponseTemplateDto getLessonWithAllInformation(@PathVariable("id") Long lessonId) {
        log.info("Inside getLessonWithAllInformation of LessonController");
        ResponseTemplateDto vo = new ResponseTemplateDto();
        if (lessonId == null) {
            log.error("lessonId was NULL");
            throw new NotFound("Lesson was NULL");
        }

        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);

        if (lessonOptional.isEmpty()) {
            log.error("Lesson with lessonId '" + lessonId + "' does not exist");
            throw new NotFound("Couldn't find lesson.");
        }

        LessonDto lesson = lessonOptional.get().getLessonDto();

        String urlSport = endpointSport + lesson.getSportName();
        String urlLocation = endpointLocation + lesson.getHallName();

        LocationDto location = restTemplate.getForObject(urlLocation, LocationDto.class);

        SportDto sport = restTemplate.getForObject(urlSport, SportDto.class);

        vo.setLesson(lesson);
        vo.setLocation(location);
        vo.setSport(sport);

        return vo;
    }

    /**
     * Gets hall.
     * URL: localhost:1304/lessons/getHall/{hallName}
     *
     * @param hallName the hall name
     * @return the hall
     */
    @GetMapping("getHall/{hallName}")
    public LocationDto getHall(@PathVariable("hallName") String hallName) {
        String url = endpointLocation + hallName;
        try {
            return restTemplate.getForObject(url, LocationDto.class);
        } catch (Exception e) {
            throw new NotFound("Couldn't find hall.");
        }
    }

    /**
     * Gets sport.
     * URL: localhost:1304/lessons/getSport/{sportName}
     *
     * @param sportName the sport name
     * @return the sport
     */
    @GetMapping("getSport/{sportName}")
    public SportDto getSport(@PathVariable("sportName") String sportName) {
        String url = endpointSport + sportName;
        try {
            return restTemplate.getForObject(url, SportDto.class);
        } catch (Exception e) {
            throw new NotFound("Couldn't find sport.");
        }
    }

    /**
     * Sets lesson repository.
     *
     * @param lessonRepository the lesson repository
     */
    public void setLessonRepository(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    /**
     * Sets rest template.
     *
     * @param restTemplate the rest template
     */
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}

