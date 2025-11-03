package nl.tudelft.sem.framework;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.application.UserDtoDetailsService;
import nl.tudelft.sem.domain.UserRepositoryAccessor;
import nl.tudelft.sem.application.securityconfigtests.AuthTestConfig;
import nl.tudelft.sem.domain.JwtAuthRequest;
import nl.tudelft.sem.domain.UserDto;
import nl.tudelft.sem.domain.UserDtoDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = AuthTestConfig.class
)
@AutoConfigureMockMvc
public class AuthControllerTest {

    String username = "username";
    String password = "password";
    UserDto userDto = new UserDto(username, password);
    UserDto userDtoEncrypted = new UserDto(username, BCrypt.hashpw(password, BCrypt.gensalt()));
    UserDtoDetails userDtoDetails = new UserDtoDetails(userDtoEncrypted);
    JwtAuthRequest request = new JwtAuthRequest(username, password);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserDtoDetailsService userDtoDetailsService;
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    UserRepositoryAccessor userRepositoryAccessor;

    @Test
    public void testSuccessfulJwtAuthentication() throws Exception {
        when(userDtoDetailsService.loadUserByUsername(username)).thenReturn(userDtoDetails);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/authenticate")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testFailedJwtAuthentication() throws Exception {
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/authenticate")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void testSuccessfulCreation() throws Exception {
        when(userRepositoryAccessor.saveUser(userDto)).thenReturn(true);
        when(userDtoDetailsService.loadUserByUsername(username)).thenReturn(userDtoDetails);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/create")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testFailedCreation() throws Exception {
        when(userRepositoryAccessor.saveUser(userDto)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/create")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void testFailedLoginCreation() throws Exception {
        when(userRepositoryAccessor.saveUser(userDto)).thenReturn(true);
        when(userDtoDetailsService.loadUserByUsername(username))
            .thenThrow(UsernameNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/create")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void testSuccessfulLogin() throws Exception {
        when(userDtoDetailsService.loadUserByUsername(username)).thenReturn(userDtoDetails);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testFailedLoginDifferentUser() throws Exception {
        UserDtoDetails compare = new UserDtoDetails(new UserDto("dummy", password));
        when(userDtoDetailsService.loadUserByUsername(username)).thenReturn(compare);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void testFailedLoginDifferentPassword() throws Exception {
        UserDtoDetails compare =
            new UserDtoDetails(new UserDto(username, BCrypt.hashpw("dummy", BCrypt.gensalt())));
        when(userDtoDetailsService.loadUserByUsername(username)).thenReturn(compare);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void testFailedLoginUsernameNotFound() throws Exception {
        when(userDtoDetailsService.loadUserByUsername(username))
            .thenThrow(UsernameNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    @Test
    public void testFailedLoginSessionException() throws Exception {
        when(userDtoDetailsService.loadUserByUsername(username)).thenReturn(userDtoDetails);
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/login")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());
    }

    /**
     * Method used to turn data into JSON format.
     *
     * @param obj is the generic data container
     * @return a JSON formatted string of param
     */
    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}