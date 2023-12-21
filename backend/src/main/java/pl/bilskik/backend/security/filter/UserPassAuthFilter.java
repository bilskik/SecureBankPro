package pl.bilskik.backend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.bilskik.backend.data.dto.UserDTO;
import pl.bilskik.backend.security.manager.AuthManager;

import java.io.IOException;

//import static pl.bilskik.backend.controller.mapping.RequestPath.LOGIN_PATH;

@Component
@Slf4j
public class UserPassAuthFilter extends OncePerRequestFilter {

    private final AuthManager authManager;

    @Autowired
    public UserPassAuthFilter(AuthManager authManager) {
        this.authManager = authManager;
    }

    @Override
    protected void doFilterInternal
            (HttpServletRequest request,
             HttpServletResponse response,
             FilterChain filterChain)
            throws ServletException, IOException {

//        if(LOGIN_PATH.equals(request.getServletPath()) && HttpMethod.POST.equals(request.getMethod())) {
//            ObjectMapper mapper = new ObjectMapper();
//            UserDTO userDTO = mapper.readValue(request.getInputStream(), UserDTO.class);
//            logger.info(userDTO);
//            SecurityContextHolder.getContext().setAuthentication(
//                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getUsername(), null)
//            );
//            SecurityContextHolder.getContext().setAuthentication(
//                    new UsernamePasswordAuthenticationToken(request.getCookies()[0].getValue(), null, null)
//            );
//        }
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(null, null, null)
        );

//            HttpSession httpSession = request.getSession(true);
//            httpSession.setAttribute("SESSION_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//            new HttpSessionSecurityContextRepository().saveContext(SecurityContextHolder.getContext(), request, response);
        filterChain.doFilter(request, response);

    }
}
