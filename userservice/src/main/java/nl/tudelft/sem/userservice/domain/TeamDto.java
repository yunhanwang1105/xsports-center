package nl.tudelft.sem.userservice.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Team dto.
 */
public class TeamDto {

    private Long id;

    private String name;

    private String joinToken;

    private String creatorId;

    private Set<UserDto> users = new HashSet<>();

    /**
     * Constructor for TeamDto.
     *
     * @param id        The id of this teamDto
     * @param name      The name of this teamDto
     * @param joinToken The joinToken of this teamDto
     * @param creatorId The creator id of this teamDto
     */
    public TeamDto(Long id, String name, String joinToken, String creatorId) {
        this.id = id;
        this.name = name;
        this.joinToken = joinToken;
        this.creatorId = creatorId;
    }

    /**
     * Constructor for TeamDto.
     */
    public TeamDto() {
    }

    /**
     * Gets the team id.
     *
     * @return The id of this teamDto
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the team id.
     *
     * @param id The id of this teamDto
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the team.
     *
     * @return The name of the team
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the team.
     *
     * @param name The team name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the join token of this team.
     *
     * @return The join token
     */
    public String getJoinToken() {
        return joinToken;
    }

    /**
     * Sets the join token.
     *
     * @param joinToken The join token
     */
    public void setJoinToken(String joinToken) {
        this.joinToken = joinToken;
    }

    /**
     * Gets the creator id.
     *
     * @return The id of the creator
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * Sets the creator id.
     *
     * @param creatorId The creator id
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * Gets the set of userDto.
     *
     * @return The users
     */
    public Set<UserDto> getUsers() {
        return users;
    }

    /**
     * Sets the set of userDto.
     *
     * @param users The users
     */
    public void setUsers(Set<UserDto> users) {
        this.users = users;
    }

    /**
     * HashCode method of team.
     *
     * @return The hash code of this team
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (joinToken != null ? joinToken.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + users.hashCode();
        return result;
    }

    /**
     * Equals method for the teamDto.
     *
     * @param o The teamDto object to compare with
     * @return True iff o is equal to this teamDto
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeamDto teamDto = (TeamDto) o;

        if (!id.equals(teamDto.id)) {
            return false;
        }
        if (name != null ? !name.equals(teamDto.name) : teamDto.name != null) {
            return false;
        }
        if (joinToken != null ? !joinToken.equals(teamDto.joinToken) : teamDto.joinToken != null) {
            return false;
        }
        if (creatorId != null ? !creatorId.equals(teamDto.creatorId) : teamDto.creatorId != null) {
            return false;
        }
        return users.equals(teamDto.users);
    }

    /**
     * ToString method of teamDto.
     *
     * @return The string representation of a teamDto
     */
    @Override
    public String toString() {
        return "TeamDto{" + "id=" + id + ", name='" + name + '\'' + ", joinToken='" + joinToken
            + '\'' + ", creatorId='" + creatorId + '\'' + ", users=" + users + '}';
    }
}
