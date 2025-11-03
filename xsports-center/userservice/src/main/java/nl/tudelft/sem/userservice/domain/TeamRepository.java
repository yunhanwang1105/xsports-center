package nl.tudelft.sem.userservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * The interface Team repository.
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // Find a team by team id.
    @Nullable
    Team findTeamById(Long id);
}
