package nl.tudelft.sem.facilityservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sport")
public class Sport {

    @Id
    @Column(name = "name")
    @JsonIgnore
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String name;

    @Column(name = "minPlayers")
    @JsonIgnore
    private int minPlayers;

    @Column(name = "maxPlayers")
    @JsonIgnore
    private int playerMax;

    /**
     * Sport constructor.
     *
     * @param name The name of the sport
     * @param minPlayers The minimal number of players
     * @param playerMax The minimal number of players
     */
    public Sport(String name, int minPlayers, int playerMax) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.playerMax = playerMax;
    }

    public Sport() {
    }

    /**
     * Sport constructor from sportDto.
     *
     * @param sportDto The sportDto to create a sport from
     */
    public Sport(SportDto sportDto) {
        this.name = sportDto.getName();
        this.minPlayers = sportDto.getMinPlayers();
        this.playerMax = sportDto.getMaxPlayers();
    }

    public SportDto getSportDto() {
        SportDto sportDto = new SportDto(name, minPlayers, playerMax);
        return sportDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sport sport = (Sport) o;
        return minPlayers == sport.minPlayers && playerMax == sport.playerMax
            && Objects.equals(name, sport.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, minPlayers, playerMax);
    }

    @Override
    public String toString() {
        return "Sport{"
             + "name='" + name + '\''
             + ", minPlayers=" + minPlayers
             + ", playerMax=" + playerMax
             + '}';
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

    public int getPlayerMax() {
        return playerMax;
    }

    public void setPlayerMax(int playerMax) {
        this.playerMax = playerMax;
    }
}
