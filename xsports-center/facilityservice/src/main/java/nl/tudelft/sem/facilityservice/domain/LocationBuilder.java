package nl.tudelft.sem.facilityservice.domain;

import java.util.HashSet;
import java.util.Set;

public class LocationBuilder implements Builder {

    private String name;
    private int min;
    private int max;
    private Set<SportDto> sports;
    private LocationType locationType;

    public void setName(String name) {
        this.name = name;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setSports(Set<SportDto> sports) {
        this.sports = sports;
    }

    public void setType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Location build() {
        Set<Sport> sportSet = new HashSet<>();
        if (sports != null) {
            for (SportDto s : sports) {
                sportSet.add(new Sport(s));
            }
        }
        return new Location(name, min, max, sportSet, locationType);
    }

    // for testing purposes
    public String getName() {
        return name;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public Set<SportDto> getSports() {
        return sports;
    }

    public LocationType getLocationType() {
        return locationType;
    }
}
