package nl.tudelft.sem.lessonservice.repository;

import java.util.List;
import nl.tudelft.sem.lessonservice.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Lesson repository.
 */
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    /**
     * Find by sport name list.
     *
     * @param sportName the sport name
     * @return the list
     */
    List<Lesson> findBySportName(String sportName);
}
