package nl.tudelft.sem.facilityservice.domain;

import java.util.Objects;

public class SportDto {
    private String name;
    private int minPlayers;
    private int maxPlayers;

    /**
     * SportDto constructor.
     *
     * @param name The name of the sport
     * @param minPlayers The minimal players
     * @param maxPlayers The maximal players
     */
    public SportDto(String name, int minPlayers, int maxPlayers) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SportDto sportDto = (SportDto) o;
        return minPlayers == sportDto.minPlayers && maxPlayers == sportDto.maxPlayers
            && Objects.equals(name, sportDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, minPlayers, maxPlayers);
    }

    @Override
    public String toString() {
        return "SportDto{"
             + "name='" + name + '\''
             + ", minPlayers=" + minPlayers
             + ", maxPlayers=" + maxPlayers
             + '}';
    }
}

