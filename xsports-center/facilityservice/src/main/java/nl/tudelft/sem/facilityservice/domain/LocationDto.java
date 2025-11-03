package nl.tudelft.sem.facilityservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LocationDto {
    private String name;
    private int min;
    private int max;
    private Set<SportDto> sports;
    private LocationType locationType;

    /**
     * LocationDto constructor.
     *
     * @param name The name of the location
     * @param min The minimal capacity
     * @param max The maximal capacity
     * @param sports The possible sports
     * @param locationType The location type
     */
    public LocationDto(String name, int min, int max, Set<SportDto> sports,
                       LocationType locationType) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.sports = sports;
        this.locationType = locationType;
    }

    public LocationDto() {
    }

    /**
     * Get a location object from this LocationDto.
     *
     * @return The location object
     */
    public Location getLocation() {
        Set<Sport> sportSet = new HashSet<>();
        if (sports != null && sports.size() != 0) {
            for (SportDto s : sports) {
                sportSet.add(new Sport(s));
            }
        }

        return new Location(name, min, max, sportSet, locationType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Set<SportDto> getSports() {
        return sports;
    }

    public void setSports(Set<SportDto> sports) {
        this.sports = sports;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    @JsonIgnore
    public boolean isValid() {
        return name != null && name.length() > 0 && min >= 0 && max > 0 && locationType != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationDto that = (LocationDto) o;
        return min == that.min && max == that.max && Objects.equals(name, that.name);
    }
}
