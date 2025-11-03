package nl.tudelft.sem.userservice.domain;

import java.util.Set;
import nl.tudelft.sem.userservice.domain.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Save User user.
     *
     * @param user the user
     * @return the user
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Find by username user.
     *
     * @param id the id
     * @return the user
     * @throws NotFoundException if user cannot be found (404)
     */
    public User findByUsername(String id) {
        User user = userRepository.findByUsername(id);
        if (user == null) {
            throw new NotFoundException("Could not find user");
        }
        return user;
    }

    /**
     * Update role boolean.
     *
     * @param username the username
     * @param role     the role
     * @return the boolean
     * @throws NotFoundException if user cannot be found (404)
     */
    public User updateRole(String username, int role) {
        User userToUpdate = userRepository.findByUsername(username);
        if (userToUpdate == null) {
            throw new NotFoundException("Could not find user");
        }
        userToUpdate.setRole(role);
        userRepository.save(userToUpdate);
        return userToUpdate;
    }

    /**
     * Gets all admins.
     *
     * @return the all admins
     */
    public Set<User> getAllAdmins() {
        return userRepository.findAllByRole(0);
    }
}
