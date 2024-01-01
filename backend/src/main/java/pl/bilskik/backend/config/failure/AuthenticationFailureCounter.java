package pl.bilskik.backend.config.failure;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import pl.bilskik.backend.config.userconfig.DetailsService;
import pl.bilskik.backend.config.userconfig.SecurityUser;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.exception.PasswordException;

import java.io.IOException;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import static pl.bilskik.backend.controller.mapping.UrlMapping.AUTH_PATH;
import static pl.bilskik.backend.controller.mapping.UrlMapping.LOGIN_FINISH_PATH;

@Configuration
@Slf4j
public class AuthenticationFailureCounter implements AccessDeniedHandler {

    private final DetailsService detailsService;
    private final UserRepository userRepository;
    private final Map<String, Integer> loginAttemptsMap;
    private final static Integer MAX_NUMBER_OF_LOGIN_ATTEMPT = 5;

    @Autowired
    public AuthenticationFailureCounter(DetailsService detailsService, UserRepository userRepository) {
        this.detailsService = detailsService;
        this.userRepository = userRepository;
        loginAttemptsMap = new HashMap<>();
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        log.error(String.valueOf(SecurityContextHolder.getContext().getAuthentication()));
        log.error(accessDeniedException.getMessage());
        if(request.getMethod().equals("POST") && request.getServletPath().equals(AUTH_PATH + LOGIN_FINISH_PATH)) {
            String username  = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            SecurityUser securityUser;
            try {
                 securityUser = detailsService.loadUserByUsername(username);
                 if(securityUser.isAccountNonLocked()) {
                     if (loginAttemptsMap.containsKey(username)) {
                         Integer currInvalidAuthAttempt = loginAttemptsMap.get(username);
                         currInvalidAuthAttempt++;
                         if (currInvalidAuthAttempt > MAX_NUMBER_OF_LOGIN_ATTEMPT) {
                             lockAccount(username);
                         } else {
                             loginAttemptsMap.put(username, currInvalidAuthAttempt);
                         }
                     } else {
                         Integer invalidAuthAttempt = 1;
                         loginAttemptsMap.put(username, invalidAuthAttempt);
                     }
                 }
            } catch(Exception ex) {
                if (response.isCommitted()) {
                    log.trace("Did not write to response since already committed");
                    return;
                }
                response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
            }
        }
        if (response.isCommitted()) {
            log.trace("Did not write to response since already committed");
            return;
        }
        response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    private void lockAccount(String username) {
        userRepository.lockAccount(username);
    }
}
