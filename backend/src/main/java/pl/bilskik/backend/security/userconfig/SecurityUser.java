package pl.bilskik.backend.security.userconfig;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.bilskik.backend.data.dto.UserDTO;
import pl.bilskik.backend.data.dto.UserLoginDTO;
import pl.bilskik.backend.service.auth.exception.PasswordException;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final UserLoginDTO user;
    private int passwordIterator;

    public SecurityUser(UserLoginDTO user) {
        this.user = user;
        passwordIterator = 0;
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
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
