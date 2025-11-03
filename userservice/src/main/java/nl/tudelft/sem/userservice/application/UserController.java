package nl.tudelft.sem.userservice.application;

import static nl.tudelft.sem.userservice.domain.communication.ServiceCommunication.addAuthentication;
import static nl.tudelft.sem.userservice.domain.communication.ServiceCommunication.isAuthenticated;

import lombok.extern.slf4j.Slf4j;
import nl.tudelft.sem.userservice.domain.User;
import nl.tudelft.sem.userservice.domain.UserDto;
import nl.tudelft.sem.userservice.domain.UserService;
import nl.tudelft.sem.userservice.domain.Utility;
import nl.tudelft.sem.userservice.domain.exceptions.NotFoundException;
import nl.tudelft.sem.userservice.domain.utilities.UserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * POST: Save a user.
     * URL: localhost:9191/users/
     *
     * @param userDto the user dto
     * @return the user dto
     */
    @PostMapping("/")
    public UserDto saveUser(@RequestBody UserDto userDto) {
        if (!addAuthentication(userDto)) {
            log.error("User authentication could not be created");
            return null;
        }

        User u = new User();
        u.setUsername(userDto.getUsername());

        // User can only be admin upon creation if there are no admins yet, otherwise a
        // user can get admin rights from another admin
        if (userDto.getRole() == 0 && userService.getAllAdmins().size() != 0) {
            log.error("User tried to sign up as admin");
            return null;
        }

        u.setRole(userDto.getRole());
        User user = userService.saveUser(u);
        return UserUtility.mapUserToUserDto(user);
    }

    /**
     * GET: Search for a user.
     * URL: localhost:9191/users/{username}
     *
     * @param username the username
     * @return the user dto
     * @throws NotFoundException if user cannot be found (404)
     */
    @GetMapping("/{username}")
    public UserDto findByUsername(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        return UserUtility.mapUserToUserDto(user);
    }

    /**
     * POST: Update a user's role.
     * URL: localhost:9191/users/updateRole?username=???&password=???
     *
     * @param userDto  the user dto with the updated role
     * @param username the username of the admin that wants to change the role
     * @param password the password of the admin that wants to change the role
     * @throws NotFoundException if user cannot be found (404)
     */
    @PostMapping("/updateRole")
    public UserDto updateRole(@RequestBody UserDto userDto, String username, String password) {
        if (!isAuthenticated(new UserDto(username, password))) {
            log.error("User is not authenticated");
            return null;
        }
        User adminUser = userService.findByUsername(username);
        if (adminUser == null || adminUser.getRole() != 0) {
            log.error("User is not admin");
            return null;
        }

        User user = userService.updateRole(userDto.getUsername(), userDto.getRole());
        return Utility.mapUserToUserDto(user);
    }
}
