package nl.tudelft.sem.domain;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.SecurityContext;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class JwtFilterTest {

    private final transient JwtFilter filter = new JwtFilter();
    @Mock
    private transient HttpServletRequest request;
    @Mock
    private transient HttpServletResponse response;
    @Mock
    private transient FilterChain filterChain;
    @Mock
    private transient JwtUtil util;
    @Mock
    private transient UserDetailsService userDetailsService;
    SecurityContext securityContext;
    Authentication auth;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);

        Field field = filter.getClass().getDeclaredField("jwtUtil");
        field.setAccessible(true);
        field.set(filter, util);

        auth = Mockito.mock(Authentication.class);


        securityContext = Mockito.mock(SecurityContext.class);
    }

    @Test
    void headerIsInvalid() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("no_bearer_prefix");
        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        verify(securityContext, times(0)).getAuthenticationScheme();
    }

    @Test
    void ExpiredJwtException() throws ServletException, IOException {
        String token = "not good";

        when(util.extractUsername(token)).thenThrow(ExpiredJwtException.class);
        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request,response);
        verify(securityContext, times(0)).getAuthenticationScheme();
    }

    @Test
    void illegalArgumentException() throws ServletException, IOException {
        String token = "Better Not Work";

        when(util.extractUsername(token)).thenThrow(IllegalArgumentException.class);
        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request,response);
        verify(securityContext, times(0)).getAuthenticationScheme();
    }

    @Test
    void authenticated() throws ServletException, IOException {
        String token = "Here we go";
        String Username = "JJWOODS";

        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
        verify(userDetailsService, times(0)).loadUserByUsername(any());
    }

    @Test
    void invalidated() throws ServletException, IOException {
        String token = "Not anymore";
        String username = "Jos";

        UserDetails userDetails = new UserDtoDetails(new UserDto(username, "password"));
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        when(securityContext.getAuthenticationScheme()).thenReturn(null);

        when(util.validateToken(token, userDetails)).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void validated() throws ServletException, IOException {
        String token = "valid";
        String username = "Joy";

        UserDetails userDetails = new UserDtoDetails(new UserDto(username, "password"));
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        when(util.validateToken(token, userDetails)).thenReturn(true);

        filter.doFilterInternal(request, response, filterChain);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        verify(filterChain, times(1)).doFilter(request, response);
    }
}