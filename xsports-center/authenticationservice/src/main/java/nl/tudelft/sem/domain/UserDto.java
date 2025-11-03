package nl.tudelft.sem.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.authentication.BadCredentialsException;

@Entity
@Table(name = "User")
public class UserDto {

    @Id
    private String username;
    private String password;

    public UserDto() {

    }

    /**
     * UserDto constructor.
     * If the username is null it will be substituted by a random UUID.
     * Password cannot be null.
     *
     * @param username is the user's unique id
     * @param password the chosen password
     */
    public UserDto(String username, String password) {
        if (username == null) {
            username = UUID.randomUUID().toString();
        }
        if (password == null) {
            throw new BadCredentialsException("Password cannot be null");
        }
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserDto)) {
            return false;
        }
        UserDto o = (UserDto) obj;
        return this.username.equals(o.username);
    }

    @Override
    public String toString() {
        return "UserDto{"
            + "username='" + username + '\''
            + ", password='" + password + '\''
            + '}';
    }
}
