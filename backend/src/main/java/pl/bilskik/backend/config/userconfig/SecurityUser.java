package pl.bilskik.backend.config.userconfig;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.bilskik.backend.data.dto.UserLoginDTO;
import pl.bilskik.backend.service.exception.PasswordException;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SecurityUser implements UserDetails {

    private final UserLoginDTO user;
    private int passwordIterator;

    public SecurityUser(UserLoginDTO user) {
        this.user = user;
        passwordIterator = 0;
    }
    public int passwordsLength() {
        return user.getPasswords().size();
    }
    @Override
    public String getPassword() {
        return getNextPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private String getNextPassword() {
        List<String> passwords = user.getPasswords();
        if(passwords == null || passwords.isEmpty()) {
            throw new PasswordException("PasswordList are null or empty!");
        }
        if(passwordIterator < passwords.size()) {
            String currPassword = user.getPasswords().get(passwordIterator);
            passwordIterator++;
            return currPassword;
        } else {
            passwordIterator = 0;
            return user.getPasswords().get(passwordIterator);
        }
    }
}
