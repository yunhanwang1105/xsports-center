package nl.tudelft.sem.facilityservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationDtoTest {

    String name;
    int min;
    int max;
    Set<SportDto> sports;
    LocationType locationType;
    LocationDto locationDto;

    @BeforeEach
    void setUp() {
        name = "Hall";
        min = 1;
        max = 10;
        sports = new HashSet<>();
        locationType = LocationType.HALL;
        locationDto = new LocationDto(name, min, max, sports, locationType);
    }

    @Test
    void emptyConstructor() {
        locationDto = new LocationDto();
        assertEquals(null, locationDto.getName());
        assertEquals(null, locationDto.getSports());
        assertEquals(null, locationDto.getLocationType());
    }

    @Test
    void getName() {
        assertEquals(locationDto.getName(), name);
    }

    @Test
    void setName() {
        locationDto.setName("Field");
        assertEquals(locationDto.getName(), "Field");
    }

    @Test
    void getMin() {
        assertEquals(locationDto.getMin(), min);
    }

    @Test
    void setMin() {
        locationDto.setMin(2);
        assertEquals(locationDto.getMin(), 2);
    }

    @Test
    void getMax() {
        assertEquals(locationDto.getMax(), max);
    }

    @Test
    void setMax() {
        locationDto.setMax(20);
        assertEquals(locationDto.getMax(), 20);
    }

    @Test
    void testEquals() {
        LocationDto locationDto1 = new LocationDto(name, min, max, sports, locationType);
        assertTrue(locationDto.equals(locationDto1));
    }

    @Test
    void setSports() {
        Set<SportDto> s = new HashSet<>();
        s.add(new SportDto("Sailing", 4, 6));

        locationDto.setSports(s);
        assertEquals(s, locationDto.getSports());
    }

    @Test
    void setLocationType() {
        locationDto.setLocationType(LocationType.FIELD);
        assertEquals(LocationType.FIELD, locationDto.getLocationType());
    }

    @Test
    void isValid() {
        assertTrue(locationDto.isValid());
    }
}