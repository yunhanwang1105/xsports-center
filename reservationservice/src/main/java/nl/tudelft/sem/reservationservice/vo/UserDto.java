package nl.tudelft.sem.reservationservice.vo;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;

    private int role;

    private Set<String> teamIds = new HashSet<>();

    /**
     * Hash code method.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return username.hashCode();
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

        UserDto userDto = (UserDto) o;

        return username.equals(userDto.username);
    }

    /**
     * ToString method.
     *
     * @return The toString
     */
    @Override
    public String toString() {
        return "UserDto{" + "username='" + username + '\'' + '}';
    }
}
