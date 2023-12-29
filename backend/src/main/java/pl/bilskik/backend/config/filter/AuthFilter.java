package pl.bilskik.backend.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.bilskik.backend.data.request.LoginRequest;
import pl.bilskik.backend.config.manager.AuthManager;

import java.io.IOException;

import static pl.bilskik.backend.controller.mapping.UrlMapping.AUTH_PATH;
import static pl.bilskik.backend.controller.mapping.UrlMapping.LOGIN_FINISH_PATH;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final AuthManager authManager;

    public AuthFilter(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equals("POST") && request.getServletPath().equals(AUTH_PATH + LOGIN_FINISH_PATH)) {
            try {
                LoginRequest userDTO = mapToUserDTO(request);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO.getUsername(),
                        userDTO.getPassword());
                Authentication resultAuthentication = authManager.authenticate(authentication);
                SecurityContextHolder.getContext().setAuthentication(resultAuthentication);
                filterChain.doFilter(request, response);
            } catch(IOException e) {
                throw new IOException("Error during parsing user!");
            }
        }
        filterChain.doFilter(request, response);
    }

    private LoginRequest mapToUserDTO(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(request.getInputStream(), LoginRequest.class);
    }
}
