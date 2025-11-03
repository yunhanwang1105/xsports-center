package nl.tudelft.sem.userservice.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.Test;

public class UserTest {

    private final String username = "name";
    private final int role = 0;
    private Set<Team> set;
    private final User user = new User(username, role, set);

    private final User secondUser = new User(username, 0, set);
    private final User thirdUser = new User("newUser", role, set);

    @Test
    public void equalsTest() {
        assertEquals(user, secondUser);
        assertNotEquals(user, thirdUser);
    }

    @Test
    public void equalsNullTest() {
        assertFalse(user.equals(null));
        assertNotEquals(user, null);
    }

    @Test
    public void equalsNotTheSameClass() {
        assertFalse(user.equals(new TeamDto()));
        assertNotEquals(user, new TeamDto());
    }

    @Test
    public void equalsUsernameNull() {
        User userUsernameNull = new User(null, role, set);
        assertFalse(userUsernameNull.equals(user));
        assertFalse(user.equals(userUsernameNull));
        assertNotEquals(user, userUsernameNull);
    }

    @Test
    public void testEquals_Symmetric() {
        assertTrue(secondUser.equals(user) && user.equals(secondUser));
        assertTrue(secondUser.hashCode() == user.hashCode());
    }

    @Test
    public void toStringTest() {
        assertEquals("User(username=name, role=0)", user.toString());
    }

}
