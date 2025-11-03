package nl.tudelft.sem.facilityservice.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "location")
public class Location {
    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "min", nullable = false)
    private int min;

    @Column(name = "max", nullable = false)
    private int max;

    @ManyToMany
    @JoinTable(
            name = "location_sport",
            joinColumns = @JoinColumn(name = "location_name"),
            inverseJoinColumns = @JoinColumn(name = "sport_name"))
    private Set<Sport> sports;

    @Column(name = "LOCATION_TYPE", nullable = false)
    private LocationType locationType;

    public Location() {
    }

    /**
     * Location constructor.
     *
     * @param name The name of the location
     * @param min The minimal team size to rent the location
     * @param max The maximal team size to rent the location
     * @param sports The sports that can be played in the location
     * @param locationType The type of the location
     */
    public Location(String name, int min, int max, Set<Sport> sports,
                    LocationType locationType) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.locationType = locationType;
        this.sports = sports;
    }

    public void addSport(Sport sport) {
        sports.add(sport);
    }

    /**
     * Get a Dto version of this location.
     *
     * @return A locationDto representing this location
     */
    public LocationDto getLocationDto() {
        LocationDto locationDto = new LocationDto(this.name, this.min, this.max,
            convertSportSet(sports), this.locationType);
        return locationDto;
    }

    private Set<SportDto> convertSportSet(Set<Sport> s) {
        Set<SportDto> sports = new HashSet<>();
        if (s != null) {
            for (Sport sport : s) {
                sports.add(sport.getSportDto());
            }
        }

        return sports;
    }

    @Override
    public String toString() {
        return "Location{"
             + "name='" + name + '\''
             + ", min=" + min
             + ", max=" + max
             + ", locationType=" + locationType
             + ", sports:" + sports
             + '}';
    }
}
