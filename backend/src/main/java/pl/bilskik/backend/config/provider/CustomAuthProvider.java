package pl.bilskik.backend.config.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bilskik.backend.config.userconfig.DetailsService;
import pl.bilskik.backend.config.userconfig.SecurityUser;

import java.util.Random;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    private final DetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final String USER_NOT_FOUND_PASSWORD = "NotF!Pass43490!.";

    public CustomAuthProvider(
            DetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = (String) authentication.getPrincipal();
        String credentials = (String) authentication.getCredentials();
        SecurityUser loadedUser;
        try {
            loadedUser = userDetailsService.loadUserByUsername(principal);
        } catch(Exception e) {
            mitigateAgainstTimingAttack(credentials);
            return authentication;
        }
        if(loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        if(!principal.equals(loadedUser.getUsername())) {
            mitigateAgainstTimingAttack(credentials);
            return authentication;
        } else {
            System.out.println(credentials);
            boolean isMatch = matchPassword(credentials, loadedUser);
            System.out.println(isMatch);
            if(isMatch) {
                return new UsernamePasswordAuthenticationToken(principal, null, null);
            } else {
                return authentication;
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

    private boolean matchPassword(String credentials, SecurityUser loadedUser) {
        for(int i=0; i<loadedUser.passwordsLength(); i++) {
            if(passwordEncoder.matches(credentials, loadedUser.getPassword())) {
                return true;
            }
        }
        return false;
    }

    private void mitigateAgainstTimingAttack(String credentials) {
        int passwordAmount = preparePasswordsAmount();
        String encodedDummyPass = passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
        for(int i=0; i<passwordAmount; i++) {
            passwordEncoder.matches(credentials, encodedDummyPass);
        }
    }

    private int preparePasswordsAmount() {
        Random random = new Random();
        return random.nextInt(20 - 15 + 1) + 15;
    }
}
