package nl.tudelft.sem.reservationservice.vo;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class TeamDto {

    private Long id;

    private String name;

    private String joinToken;

    private String creatorId;

    private Set<UserDto> users;

    /**
     * Hash code method.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return id.hashCode();
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

        TeamDto teamDto = (TeamDto) o;

        return id.equals(teamDto.id);
    }

    /**
     * ToString method.
     *
     * @return The toString
     */
    @Override
    public String toString() {
        return "TeamDto{" + "id=" + id + '}';
    }
}
