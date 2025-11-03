package nl.tudelft.sem.userservice.domain;

import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "joinToken", nullable = false)
    private String joinToken;

    @Column(name = "creatorId", nullable = false)
    private String creatorId;

    @ManyToMany
    @JoinTable(name = "team_user", joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "user_username"))
    @ToString.Exclude
    private Set<User> users;

    /**
     * HashCode method of team.
     *
     * @return The hash code of this team
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * Equals method for the team.
     *
     * @param o The team object to compare with
     * @return True iff o is equal to this team
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Team team = (Team) o;
        return id != null && Objects.equals(id, team.id);
    }
}
