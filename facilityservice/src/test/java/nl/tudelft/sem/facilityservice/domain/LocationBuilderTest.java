package nl.tudelft.sem.facilityservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class LocationBuilderTest {

    String name = "X1";
    int min = 2;
    int max = 4;
    Set<SportDto> sports = new HashSet<>();
    LocationType locationType = LocationType.FIELD;

    @Test
    void setNameTest() {
        Builder builder = new LocationBuilder();
        builder.setName(name);
        assertEquals(builder.getName(), name);
    }

    @Test
    void setMinTest() {
        Builder builder = new LocationBuilder();
        builder.setMin(min);
        assertEquals(builder.getMin(), min);
    }

    @Test
    void setMaxTest() {
        Builder builder = new LocationBuilder();
        builder.setMax(max);
        assertEquals(builder.getMax(), max);
    }

    @Test
    void setSportsTest() {
        Builder builder = new LocationBuilder();
        builder.setSports(sports);
        assertEquals(builder.getSports(), sports);
    }

    @Test
    void setLocationTypeTest() {
        Builder builder = new LocationBuilder();
        builder.setType(locationType);
        assertEquals(builder.getLocationType(), locationType);
    }

    @Test
    void build() {
        Builder builder = new LocationBuilder();
        builder.setName(name);
        builder.setMin(min);
        builder.setMax(max);
        builder.setSports(sports);
        builder.setType(locationType);
        assertEquals(new Location(name, min, max, new HashSet<>(), locationType), builder.build());
    }

}
