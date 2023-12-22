package pl.bilskik.backend.security.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import pl.bilskik.backend.security.provider.CustomAuthProvider;

import java.security.AuthProvider;

@Component
public class AuthManager implements AuthenticationManager {

    private final CustomAuthProvider authProvider;

    public AuthManager(CustomAuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authProvider.supports(authentication.getClass())) {
            return authProvider.authenticate(authentication);
        } else {
            throw new BadCredentialsException("Cannot authenticate user!");
        }
    }
}
