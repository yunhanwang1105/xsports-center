package nl.tudelft.sem.reservationservice.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class SportDto {

    private String name;

    private int minPlayers;

    private int maxPlayers;

    /**
     * Hash code method.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Equals method for this object.
     *
     * @param o The object
     * @return True iff equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SportDto sportDto = (SportDto) o;

        return name.equals(sportDto.name);
    }

    /**
     * ToString method.
     *
     * @return The toString
     */
    @Override
    public String toString() {
        return "SportDto{" + "name='" + name + '\'' + '}';
    }
}
