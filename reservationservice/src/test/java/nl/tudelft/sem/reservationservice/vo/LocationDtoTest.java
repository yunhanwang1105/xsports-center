package nl.tudelft.sem.reservationservice.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

    // Test name getter
    @Test
    void getName() {
        assertEquals(locationDto.getName(), name);
    }

    // Test name setter
    @Test
    void setName() {
        locationDto.setName("Field");
        assertEquals(locationDto.getName(), "Field");
    }

    // Test min getter
    @Test
    void getMin() {
        assertEquals(locationDto.getMin(), min);
    }

    // Test min setter
    @Test
    void setMin() {
        locationDto.setMin(2);
        assertEquals(locationDto.getMin(), 2);
    }

    // Test max getter
    @Test
    void getMax() {
        assertEquals(locationDto.getMax(), max);
    }

    // Test max setter
    @Test
    void setMax() {
        locationDto.setMax(20);
        assertEquals(locationDto.getMax(), 20);
    }

    // Test equals method 1
    @Test
    void testEquals() {
        LocationDto locationDto1 = new LocationDto(name, min, max, sports, locationType);
        assertEquals(locationDto, locationDto1);
        assertEquals(locationDto, locationDto);
    }

    // Test equals method 2
    @Test
    void testEquals2() {
        LocationDto locationDto1 = new LocationDto("X3", min, 5, sports, locationType);
        assertNotEquals(locationDto, locationDto1);
        assertFalse(locationDto.equals(null));
        assertFalse(locationDto.equals("s"));
    }

    // Test sports getter
    @Test
    void getSports() {
        assertEquals(0, locationDto.getSports().size());
    }

    // Test locationType getter
    @Test
    void getLocationType() {
        assertEquals(LocationType.HALL, locationDto.getLocationType());
    }

    // Test sports getter
    @Test
    void setSports() {
        locationDto.setSports(new HashSet<>(Set.of(new SportDto("tennis", 2, 4))));
        assertEquals(1, locationDto.getSports().size());
    }

    // Test locationType setter
    @Test
    void setLocationType() {
        locationDto.setLocationType(LocationType.FIELD);
        assertEquals(LocationType.FIELD, locationDto.getLocationType());
    }

    // Test hashCode
    @Test
    void testHashCode() {
        int code = locationDto.hashCode();
        assertEquals(code, locationDto.hashCode());
        LocationDto locationDto2 = new LocationDto(null, min, 5, sports, locationType);
    }

    // Test toString
    @Test
    void testToString() {
        assertEquals("LocationDto{name='Hall'}", locationDto.toString());
    }
}