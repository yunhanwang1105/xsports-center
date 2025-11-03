package nl.tudelft.sem.application.securityconfigtests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.tudelft.sem.application.UserDtoDetailsService;
import nl.tudelft.sem.domain.UserRepositoryAccessor;
import nl.tudelft.sem.domain.UserDto;
import nl.tudelft.sem.domain.UserDtoDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
public class SecurityConfigIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserDtoDetailsService userDtoDetailsService;

    @MockBean
    UserRepositoryAccessor userRepositoryAccessor;

    String username = "username";
    String password = "password";
    UserDto userDto = new UserDto(username, password);
    UserDto userDtoEncrypted = new UserDto(username, BCrypt.hashpw(password, BCrypt.gensalt()));
    UserDtoDetails userDtoDetails = new UserDtoDetails(userDtoEncrypted);

    /**
     * This test is for the setup of securityConfig and authentication of users.
     * By using authentication/create it is possible to test all three controller methods
     * and see if a generic user is permitted to access the controller.
     *
     * @throws Exception if perform fails.
     */
    @Test
    public void testAuthorizedCreate() throws Exception {
        when(userRepositoryAccessor.saveUser(userDto)).thenReturn(true);
        when(userDtoDetailsService.loadUserByUsername(username)).thenReturn(userDtoDetails);
        mockMvc.perform(MockMvcRequestBuilders.post("/authentication/create")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());
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