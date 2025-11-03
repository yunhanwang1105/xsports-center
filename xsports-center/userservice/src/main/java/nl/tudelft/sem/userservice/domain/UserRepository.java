package nl.tudelft.sem.userservice.domain;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Find a user by username.
     *
     * @param username the username
     * @return the user
     */
    // Find user by its username.
    @Nullable
    User findByUsername(String username);

    /**
     * Find all users with a specific role.
     *
     * @param role the role
     * @return the set
     */
    // Find all users by their role.
    Set<User> findAllByRole(int role);

}
