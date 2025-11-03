package nl.tudelft.sem.facilityservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationTest {
    private String name;
    private int min;
    private int max;
    private Set<Sport> sports;
    private Location location;
    private LocationType locationType;

    @BeforeEach
    void setUp() {
        name = "Hall";
        min = 10;
        max = 100;
        sports = new HashSet<>();
        locationType = LocationType.HALL;

        location = new Location(name, min, max, new HashSet<>(), locationType);
    }

    @Test
    void emptyConstructor() {
        Location l = new Location();
        assertEquals(null, l.getName());
        assertEquals(null, l.getSports());
        assertEquals(null, l.getLocationType());
    }

    @Test
    void addSport() {
        Sport s = new Sport(new SportDto("Basketball", 2, 10));
        location.addSport(s);
        assertTrue(location.getSports().contains(s));
    }

    @Test
    void getLocationDto() {
        assertEquals(new LocationDto(name, min, max, new HashSet<>(), locationType),
            location.getLocationDto());
    }

    @Test
    void testEquals() {
        Location location2 =
            new Location(name, min, max, new HashSet<>(), locationType);
        assertTrue(location.equals(location2));
        assertFalse(location.equals(
            new Location(name, 3, max, new HashSet<>(), locationType)));
    }

    @Test
    void getName() {
        assertEquals(name, location.getName());
    }

    @Test
    void getMin() {
        assertEquals(min, location.getMin());
    }

    @Test
    void getMax() {
        assertEquals(max, location.getMax());
    }

    @Test
    void getSports() {
        assertEquals(sports, location.getSports());
    }

    @Test
    void setSports() {
        Set<Sport> s = new HashSet<>();
        s.add(new Sport(new SportDto("sport", 0, 69)));

        location.setSports(s);
        assertEquals(s, location.getSports());
    }

    @Test
    void nonemptySports() {
        Set<SportDto> sDto = new HashSet<>();
        sDto.add(new SportDto("sport", 0, 69));
        Set<Sport> s = new HashSet<>();
        s.add(new Sport("sport", 0, 69));

        location = new Location(name, min, max, s, locationType);
        assertEquals(new LocationDto(name, min, max, sDto, locationType), location.getLocationDto());
    }

    @Test
    void setName() {
        location.setName("bored");
        assertEquals("bored", location.getName());
    }

    @Test
    void setType() {
        location.setLocationType(LocationType.FIELD);
        assertEquals(LocationType.FIELD, location.getLocationType());
    }

    @Test
    void setMin() {
        location.setMin(1);
        assertEquals(1, location.getMin());
    }

    @Test
    void setMax() {
        location.setMax(1000);
        assertEquals(1000, location.getMax());
    }

    @Test
    void toStringTest() {
        location = new Location("LOCATION", 0, 42, null, LocationType.HALL);
        assertEquals("Location{name='LOCATION', min=0, max=42, locationType=HALL, sports:null}",
            location.toString());
    }
}