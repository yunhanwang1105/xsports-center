package nl.tudelft.sem.lessonservice.communication;

/**
 * The type Credentials.
 */
public class Credentials {
    /**
     * The Username.
     */
    String username;
    /**
     * The Password.
     */
    String password;
    /**
     * The Role.
     */
    int role;

    /**
     * Instantiates new Credentials.
     *
     * @param username the username
     * @param password the password
     * @param role     the role
     */
    public Credentials(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Instantiates new Credentials.
     *
     * @param username the username
     * @param password the password
     */
    public Credentials(String username, String password) {
        this(username, password, -1);
    }

    //    /**
    //     * Instantiates a new Credentials.
    //     */
    //    public Credentials() {}

    //    /**
    //     * Gets username.
    //     *
    //     * @return the username
    //     */
    //    public String getUsername() {
    //        return username;
    //    }

    //    /**
    //     * Sets username.
    //     *
    //     * @param username the username
    //     */
    //    public void setUsername(String username) {
    //        this.username = username;
    //    }

    //    /**
    //     * Gets password.
    //     *
    //     * @return the password
    //     */
    //    public String getPassword() {
    //        return password;
    //    }

    //    /**
    //     * Sets password.
    //     *
    //     * @param password the password
    //     */
    //    public void setPassword(String password) {
    //        this.password = password;
    //    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public int getRole() {
        return role;
    }

    //    /**
    //     * Sets role.
    //     *
    //     * @param role the role
    //     */
    //    public void setRole(int role) {
    //        this.role = role;
    //    }
}
