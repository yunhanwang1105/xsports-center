package nl.tudelft.sem.reservationservice.communication;

public class Credentials {
    String username;
    String password;
    int role;

    /**
     * Constructor method for Credential class.
     *
     * @param username The username
     * @param password The password
     * @param role     The role
     */
    public Credentials(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructor method for Credential class.
     *
     * @param username The username
     * @param password The password
     */
    public Credentials(String username, String password) {
        this(username, password, -1);
    }

    /**
     * Default constructor for Credential class.
     */
    public Credentials() {}

    /**
     * Gets the username.
     *
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username The username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the role.
     *
     * @return The role
     */
    public int getRole() {
        return role;
    }

    /**
     * Sets the role.
     *
     * @param role The role
     */
    public void setRole(int role) {
        this.role = role;
    }
}
