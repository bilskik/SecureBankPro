package pl.bilskik.backend.config.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import pl.bilskik.backend.config.provider.CustomAuthProvider;

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
