package nl.tudelft.sem.facilityservice.domain;

import java.util.Set;

public interface Builder {
    void setName(String name);

    void setMin(int min);

    void setMax(int max);

    void setSports(Set<SportDto> sports);

    void setType(LocationType locationType);

    Location build();

    // for testing purposes
    String getName();

    int getMin();

    int getMax();

    Set<SportDto> getSports();

    LocationType getLocationType();
}
