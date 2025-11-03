package nl.tudelft.sem.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDtoDetails implements UserDetails {

    private final UserDto user;
    private static final long serialVersionUID = 123456789L;

    public UserDtoDetails(UserDto user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return user.hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections
          .singletonList(new SimpleGrantedAuthority("basic_role"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDto getUser() {
        return this.user;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UserDtoDetails)) {
            return false;
        }
        UserDtoDetails o = (UserDtoDetails) obj;
        return this.user.equals(o.getUser());
    }

}
