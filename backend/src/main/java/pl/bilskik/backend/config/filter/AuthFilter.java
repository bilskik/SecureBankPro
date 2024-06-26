package pl.bilskik.backend.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.bilskik.backend.config.manager.AuthManager;
import pl.bilskik.backend.data.request.LoginRequest;

import java.io.IOException;

import static pl.bilskik.backend.controller.mapping.UrlMapping.LOGIN_FINISH_URL;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final AuthManager authManager;
    private final long DELAY_AUTH_TIME_IN_MS = 500;

    public AuthFilter(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if(request.getMethod().equals(HttpMethod.POST.name()) && request.getServletPath().equals(LOGIN_FINISH_URL)) {
            try {
                handleAuthDelay();
                LoginRequest userDTO = mapToUserDTO(request);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(),
                        userDTO.getPassword()
                );
                Authentication resultAuthentication = authManager.authenticate(authentication);
                SecurityContextHolder.getContext().setAuthentication(resultAuthentication);
                filterChain.doFilter(request, response);
            } catch(IOException e) {
                throw new IOException("Error during parsing user!");
            }
        }

        filterChain.doFilter(request, response);
    }

    private void handleAuthDelay() {
        try {
            Thread.sleep(DELAY_AUTH_TIME_IN_MS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private LoginRequest mapToUserDTO(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(request.getInputStream(), LoginRequest.class);
    }
}
