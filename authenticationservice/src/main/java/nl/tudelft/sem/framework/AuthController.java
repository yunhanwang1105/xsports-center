package nl.tudelft.sem.framework;

import nl.tudelft.sem.application.UserDtoDetailsService;
import nl.tudelft.sem.domain.JwtAuthRequest;
import nl.tudelft.sem.domain.JwtAuthResponse;
import nl.tudelft.sem.domain.JwtUtil;
import nl.tudelft.sem.domain.UserDto;
import nl.tudelft.sem.domain.UserDtoDetails;
import nl.tudelft.sem.domain.UserRepositoryAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/authentication")
@RestController
public class AuthController {

    private final transient ResponseEntity.BodyBuilder failedRequest = ResponseEntity
        .status(HttpStatus.FORBIDDEN);

    private final transient ResponseEntity.BodyBuilder acceptedRequest = ResponseEntity
        .status(HttpStatus.ACCEPTED);

    private final transient ResponseEntity<String> incorrectUsernameOrPassword = failedRequest
        .body("Incorrect Username or Password");

    @Autowired
    private transient AuthenticationManager authenticationManager;

    @Autowired
    private transient UserRepositoryAccessor userRepositoryAccessor;

    @Autowired
    private transient UserDtoDetailsService userDtoDetailsService;

    @Autowired
    private transient JwtUtil jwtTokenUtil;

    /**
     * POST: Authenticates user based on JWT.
     * URL: localhost:9191/authentication/authenticate
     *
     * @param jwtAuthRequest The JWT auth request
     * @return A new JWT token
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthRequest jwtAuthRequest)
        throws Exception {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    jwtAuthRequest.getUsername(),
                    jwtAuthRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return incorrectUsernameOrPassword;
        }
        final UserDtoDetails userDtoDetails = userDtoDetailsService
            .loadUserByUsername(jwtAuthRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDtoDetails);

        return ResponseEntity.ok(new JwtAuthResponse(jwt));
    }

    /**
     * POST: Mapping of user creation, given userDto with username and password.
     * If the user is successfully created in the db and automatically logged in, return code 2xx.
     * If the db contains the user already or automatic login fails, return a FORBIDDEN status.
     * URL: localhost:9191/authentication/create
     *
     * @param userDto is the given user instance
     * @return a response entity with text body, either ACCEPTED or FORBIDDEN.
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> createAccount(@RequestBody UserDto userDto) {
        if (userRepositoryAccessor.saveUser(userDto)) {
            if (login(userDto).getStatusCode().is2xxSuccessful()) {
                return acceptedRequest
                    .body("Account successfully created, you are now logged in");
            } else {
                return failedRequest
                    .body("Account was created, but login failed");
            }
        } else {
            return failedRequest
                .body("Username: " + userDto.getUsername() + " is already in use");
        }
    }

    /**
     * POST: Mapping for user login given UserDto.
     * Checks if the user is contained in the db and compares password.
     * Then creates a JWT session token.
     * If any of the steps above fail, return a FORBIDDEN status.
     * URL: localhost:9191/authentication/login
     *
     * @param userDto the given user
     * @return a response entity with text body, either ACCEPTED or FORBIDDEN
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        try {
            final UserDtoDetails newUser = userDtoDetailsService
                .loadUserByUsername(userDto.getUsername());

            if (!checkCredentials(userDto, newUser)) {
                return incorrectUsernameOrPassword;
            }
            if (!checkToken(userDto)) {
                throw new Exception();
            }

            return acceptedRequest
                .body("Login successful");
        } catch (UsernameNotFoundException e) {
            return incorrectUsernameOrPassword;
        } catch (Exception e) {
            return failedRequest
                .body("Session was not created successfully");
        }
    }

    private boolean checkCredentials(UserDto userDto, UserDtoDetails other) {
        return other.getUser().equals(userDto)
            && BCrypt.checkpw(userDto.getPassword(), other.getPassword());
    }

    private boolean checkToken(UserDto userDto) throws Exception {
        return createAuthenticationToken(new JwtAuthRequest(userDto.getUsername(),
            userDto.getPassword())).getStatusCode().is2xxSuccessful();
    }
}
