package nl.tudelft.sem.userservice.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * The type User dto.
 */
public class UserDto {
    private String username;
    private String password;

    private int role;

    private Set<String> teamIds = new HashSet<>();

    /**
     * Instantiates a new User dto.
     */
    public UserDto() {

    }

    /**
     * The constructor of a UserDto.
     *
     * @param username The username
     * @param password The password
     * @param role     The role of this user
     */
    public UserDto(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * The constructor of a UserDto.
     *
     * @param username The username
     * @param role     The role of this user
     */
    public UserDto(String username, int role) {
        this(username, null, role);
    }

    /**
     * The constructor of a UserDto.
     *
     * @param username the username
     * @param password the password
     */
    public UserDto(String username, String password) {
        this(username, password, -1);
    }

    /**
     * Gets username.
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets password.
     *
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets role.
     *
     * @return The role
     */
    public int getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role The role
     */
    public void setRole(int role) {
        this.role = role;
    }

    /**
     * Gets a set of team ids that this user is in.
     *
     * @return The set of team ids
     */
    public Set<String> getTeamIds() {
        return teamIds;
    }

    /**
     * Sets the sets of team ids.
     *
     * @param teamIds The set of team ids
     */
    public void setTeamIds(Set<String> teamIds) {
        this.teamIds = teamIds;
    }

    /**
     * HashCode method of userDto.
     *
     * @return The hash code of userDto
     */
    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + role;
        result = 31 * result + (teamIds != null ? teamIds.hashCode() : 0);
        return result;
    }

    /**
     * Equals method for userDto.
     *
     * @param o The user object to compare with
     * @return True iff o is equal to this user
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDto userDto = (UserDto) o;

        if (role != userDto.role) {
            return false;
        }
        if (!username.equals(userDto.username)) {
            return false;
        }
        return teamIds != null ? teamIds.equals(userDto.teamIds) : userDto.teamIds == null;
    }

    /**
     * ToString method of userDto.
     *
     * @return The string representation of a userDto
     */
    @Override
    public String toString() {
        return "UserDto{" + "username='" + username + '\'' + ", role=" + role + ", teamIds="
            + teamIds + '}';
    }
}
