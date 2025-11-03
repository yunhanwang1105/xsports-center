package nl.tudelft.sem.lessonservice.vo;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * The type Location DTO.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor

public class LocationDto {

    private String name;

    private int min;

    private int max;

    private Set<SportDto> sports;
}
