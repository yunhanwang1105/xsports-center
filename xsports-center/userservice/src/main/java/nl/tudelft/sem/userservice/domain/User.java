package nl.tudelft.sem.userservice.domain;

import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

/**
 * The type User.
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
public class User {
    @Id
    //    @GeneratedValue(strategy = GenerationType.AUTO)
    private String username;

    @Column(name = "role", nullable = false)
    private int role;

    @ManyToMany(mappedBy = "users")
    @ToString.Exclude
    private Set<Team> teams;

    /**
     * HashCode method of user.
     *
     * @return The hash code of user
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * Equals method for user.
     *
     * @param o The user object to compare with
     * @return True iff o is equal to this user
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User user = (User) o;
        return username != null && Objects.equals(username, user.username);
    }
}
