package nl.tudelft.sem.domain;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryAccessor {

    @Autowired
    private transient UserRepository userRepository;

    public Optional<UserDto> loadByUsername(String username) {
        return userRepository.findById(username);
    }

    public Optional<UserDto> loadByUser(UserDto user) {
        return loadByUsername(user.getUsername());
    }

    /**
     * This method takes in a user instance and tries storing the user in the db.
     * If the db did not previously contain the user then the method will return true,
     * otherwise it will return false.
     *
     * @param user is the user to be saved.
     * @return true upon successful storing.
     */
    public boolean saveUser(UserDto user) {
        if (loadByUser(user).isPresent()) {
            return false;
        }
        userRepository.save(user);
        return true;
    }
}
