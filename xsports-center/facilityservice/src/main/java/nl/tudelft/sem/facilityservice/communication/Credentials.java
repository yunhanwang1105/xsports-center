package nl.tudelft.sem.facilityservice.communication;

public class Credentials {
    String username;
    String password;
    int role;

    /**
     * Credentials constructor.
     *
     * @param username The username
     * @param password The password
     * @param role The role
     */
    public Credentials(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Credentials(String username, String password) {
        this(username, password, -1);
    }

    public Credentials() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
