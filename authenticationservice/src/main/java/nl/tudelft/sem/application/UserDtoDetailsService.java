package nl.tudelft.sem.application;

import java.util.Optional;
import nl.tudelft.sem.domain.UserDto;
import nl.tudelft.sem.domain.UserDtoDetails;
import nl.tudelft.sem.domain.UserRepositoryAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserDtoDetailsService implements UserDetailsService {

    @Autowired
    private transient RestTemplate restTemplate;

    @Autowired
    private UserRepositoryAccessor userRepositoryAccessor;

    @Override
    public UserDtoDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDto> user = userRepositoryAccessor.loadByUsername(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        return new UserDtoDetails(user.get());
    }
}
