package pl.bilskik.backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.mapper.Mapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.bilskik.backend.data.dto.UserDTO;
import pl.bilskik.backend.security.manager.AuthManager;

import java.io.IOException;
import java.io.InputStream;

import static pl.bilskik.backend.controller.mapping.RequestPath.AUTH_PATH;
import static pl.bilskik.backend.controller.mapping.RequestPath.LOGIN_FINISH_PATH;

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
                UserDTO userDTO = mapToUserDTO(request);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO.getUsername(),
                        userDTO.getPassword());
                Authentication resultAuthentication = authManager.authenticate(authentication);
                if(resultAuthentication.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(resultAuthentication);
                    filterChain.doFilter(request, response);
                } else {
                    SecurityContextHolder.getContext().setAuthentication(resultAuthentication);
                    filterChain.doFilter(request, response);
                }
            } catch(IOException e) {
                throw new IOException("Error during parsing user!");
            }
        } else {
//            SecurityContextHolder.getContext().setAuthentication(
//                    new UsernamePasswordAuthenticationToken(null, null ,null));
            filterChain.doFilter(request, response);
        }
    }

    private UserDTO mapToUserDTO(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(request.getInputStream(), UserDTO.class);
    }
}
