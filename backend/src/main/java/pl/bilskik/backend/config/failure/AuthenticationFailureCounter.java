package pl.bilskik.backend.config.failure;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import pl.bilskik.backend.config.userconfig.DetailsService;
import pl.bilskik.backend.config.userconfig.SecurityUser;
import pl.bilskik.backend.repository.UserRepository;

import java.io.IOException;
import java.util.Map;

import static pl.bilskik.backend.controller.mapping.UrlMapping.AUTH_PATH;
import static pl.bilskik.backend.controller.mapping.UrlMapping.LOGIN_FINISH_PATH;

@Configuration
@Slf4j
public class AuthenticationFailureCounter implements AccessDeniedHandler {

    private final DetailsService detailsService;
    private final UserRepository userRepository;
    private final Map<String, Integer> loginAttemptsHolder;
    private final LoginAttemptsContainer loginAttemptsContainer;
    private final static Integer MAX_NUMBER_OF_LOGIN_ATTEMPT = 5;

    @Autowired
    public AuthenticationFailureCounter(
            DetailsService detailsService,
            UserRepository userRepository,
            LoginAttemptsContainer loginAttemptsContainer
    ) {
        this.detailsService = detailsService;
        this.userRepository = userRepository;
        this.loginAttemptsHolder = loginAttemptsContainer.getLoginAttemptsHolder();
        this.loginAttemptsContainer = loginAttemptsContainer;
    }

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        if(request.getMethod().equals(HttpMethod.POST.name()) && request.getServletPath().equals(AUTH_PATH + LOGIN_FINISH_PATH)) {
            try {
                String username  = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                SecurityUser securityUser = detailsService.loadUserByUsername(username);
                 if(securityUser.isAccountNonLocked()) {
                     if (loginAttemptsHolder.containsKey(username)) {
                         Integer currInvalidAuthAttempt = loginAttemptsHolder.get(username);
                         currInvalidAuthAttempt++;
                         if (currInvalidAuthAttempt > MAX_NUMBER_OF_LOGIN_ATTEMPT) {
                             lockAccount(username);
                         } else {
                             loginAttemptsHolder.put(username, currInvalidAuthAttempt);
                         }
                     } else {
                         Integer invalidAuthAttempt = 1;
                         loginAttemptsHolder.put(username, invalidAuthAttempt);
                     }
                     loginAttemptsContainer.commitChanges(loginAttemptsHolder);
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
