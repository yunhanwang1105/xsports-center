package nl.tudelft.sem.facilityservice.domain;

import java.util.Set;

public class Director {

    /**
     * Construct a hall.
     *
     * @param builder The builder to use
     * @param name The name of the hall
     * @param min The minimal persons
     * @param max The maximal persons
     */
    public void constructHallLocation(Builder builder, String name, int min, int max) {
        builder.setName(name);
        builder.setMin(min);
        builder.setMax(max);
        Set<SportDto> sports = null;
        builder.setSports(sports);
        builder.setType(LocationType.HALL);
    }

    /**
     * Construct a field.
     *
     * @param builder The builder to use
     * @param name The name of the hall
     * @param min The minimal persons
     * @param max The maximal persons
     */
    public void constructFieldLocation(Builder builder, String name, int min, int max) {
        builder.setName(name);
        builder.setMin(min);
        builder.setMax(max);
        Set<SportDto> sports = null;
        builder.setSports(sports);
        builder.setType(LocationType.FIELD);
    }

}