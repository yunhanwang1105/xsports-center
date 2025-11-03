package nl.tudelft.sem.facilityservice.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import nl.tudelft.sem.facilityservice.domain.Sport;
import nl.tudelft.sem.facilityservice.domain.SportDto;
import nl.tudelft.sem.facilityservice.domain.SportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SportControllerTest {
    private final String name = "Baseball";
    private final int minPlayers = 9;
    private final int maxPlayers = 18;
    private final Sport sport = new Sport(new SportDto(name, minPlayers, maxPlayers));

    @Mock
    private SportRepository sportRepository;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveSport() {
        when(sportRepository.save(sport)).thenReturn(sport);
        assertEquals(sport, sportRepository.save(sport));
        assertNotNull(sportRepository.findAll());
    }

    @Test
    void findSportByName() {
        when(sportRepository.findById(name)).thenReturn(Optional.ofNullable(sport));
        assertEquals(Optional.of(sport), sportRepository.findById(name));
    }

    @Test
    void isAuthenticated() {
    }
}