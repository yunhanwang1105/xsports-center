package nl.tudelft.sem.lessonservice.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationDtoTest {
    private LocationDto locationDto;
    private String name;
    private int min;
    private int max;
    private Set<SportDto> sports;

    @BeforeEach
    void setUp() {
        name = "pool";
        min = 1;
        max = 50;
        sports = new HashSet<>();
        locationDto = new LocationDto(name, min, max, sports);
    }

    @Test
    void getName() {
        assertEquals(name, locationDto.getName());
    }

    @Test
    void getMin() {
        assertEquals(min, locationDto.getMin());
    }

    @Test
    void getMax() {
        assertEquals(max, locationDto.getMax());
    }

    @Test
    void getSports() {
        assertEquals(sports, locationDto.getSports());
    }

    @Test
    void setName() {
        locationDto.setName("Court");
        assertEquals("Court", locationDto.getName());
    }

    @Test
    void setMin() {
        locationDto.setMin(2);
        assertEquals(2, locationDto.getMin());
    }

    @Test
    void setMax() {
        locationDto.setMax(100);
        assertEquals(100, locationDto.getMax());
    }

    @Test
    void setSports() {
        Set<SportDto> sports1 = new HashSet<>();
        locationDto.setSports(sports1);
        assertEquals(sports1, locationDto.getSports());
    }

    @Test
    void testEquals() {
        LocationDto locationDto1 = new LocationDto(name, min, max, sports);
        assertEquals(locationDto1, locationDto);
        LocationDto locationDto2 = new LocationDto(name, 12, max, sports);
        assertNotEquals(locationDto2, locationDto);

    }

    @Test
    void testHashCode() {
        LocationDto locationDto1 = new LocationDto(name, min, max, sports);
        assertEquals(locationDto.hashCode(), locationDto1.hashCode());
    }
}