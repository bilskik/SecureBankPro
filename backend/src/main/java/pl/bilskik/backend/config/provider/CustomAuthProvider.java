package pl.bilskik.backend.config.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bilskik.backend.config.failure.AuthenticationFailureCounter;
import pl.bilskik.backend.config.failure.LoginAttemptsContainer;
import pl.bilskik.backend.config.userconfig.DetailsService;
import pl.bilskik.backend.config.userconfig.SecurityUser;

import java.util.Map;
import java.util.Random;

@Component
public class CustomAuthProvider implements AuthenticationProvider {

    private final DetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final LoginAttemptsContainer loginAttemptsContainer;
    private final String USER_NOT_FOUND_PASSWORD = "NotF!Pass43490!.";

    public CustomAuthProvider(
            DetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            LoginAttemptsContainer loginAttemptsContainer

    ) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptsContainer = loginAttemptsContainer;
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
        if(!isUserAccountValid(loadedUser) || !principal.equals(loadedUser.getUsername())) {
            mitigateAgainstTimingAttack(credentials);
            return authentication;
        } else {
            System.out.println(credentials);
            boolean isMatch = matchPassword(credentials, loadedUser);
            System.out.println(isMatch);
            if(isMatch) {
                updateLoginAttemptsContainer(principal);
                return new UsernamePasswordAuthenticationToken(principal, null, loadedUser.getAuthorities());
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

    private boolean isUserAccountValid(SecurityUser loadedUser) {
        if(!loadedUser.isAccountNonLocked() || !loadedUser.isAccountNonExpired()
                || !loadedUser.isEnabled() || !loadedUser.isCredentialsNonExpired()) {
            return false;
        }
        return true;
    }
    private void updateLoginAttemptsContainer(String username) {
        Map<String, Integer> loginAttemptsHolder = loginAttemptsContainer.getLoginAttemptsHolder();
        if(loginAttemptsHolder.containsKey(username)) {
            Integer loginAttempts = 0;
            loginAttemptsHolder.put(username, loginAttempts);
            loginAttemptsContainer.commitChanges(loginAttemptsHolder);
        }
    }

}
