package nl.tudelft.sem.lessonservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.tudelft.sem.lessonservice.entity.LessonDto;

/**
 * The type Response template DTO.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseTemplateDto {

    private LessonDto lesson;
    private LocationDto location;
    private SportDto sport;
}
