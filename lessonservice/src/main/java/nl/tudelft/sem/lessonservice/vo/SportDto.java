package nl.tudelft.sem.lessonservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * The type Sport DTO.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor

public class SportDto {

    private String name;

    private int minPlayers;

    private int maxPlayers;
}
