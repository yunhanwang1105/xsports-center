package nl.tudelft.sem.facilityservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import org.junit.jupiter.api.Test;

public class DirectorTest {

    Builder builder = new LocationBuilder();
    Director director = new Director();
    String name = "X1";
    int min = 2;
    int max = 10;

    @Test
    void constructHallTest() {
        director.constructHallLocation(builder, name, min, max);
        assertEquals(new Location(name, min, max, new HashSet<>(), LocationType.HALL), builder.build());
    }

    @Test
    void constructFieldTest() {
        director.constructFieldLocation(builder, name, min, max);
        assertEquals(new Location(name, min, max, new HashSet<>(), LocationType.FIELD), builder.build());
    }

}
