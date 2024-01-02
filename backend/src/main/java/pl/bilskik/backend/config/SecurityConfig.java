package pl.bilskik.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.bilskik.backend.config.failure.AuthenticationFailureCounter;
import pl.bilskik.backend.config.filter.AuthFilter;
import pl.bilskik.backend.config.manager.AuthManager;
import java.util.Arrays;

import static pl.bilskik.backend.controller.mapping.UrlMapping.*;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthFilter authFilter;
    private final AuthManager authenticationManager;
    private final AuthenticationFailureCounter authenticationFailureCounter;

    @Autowired
    public SecurityConfig(AuthFilter authFilter,
                          AuthManager authenticationManager,
                          AuthenticationFailureCounter authenticationFailureCounter) {
        this.authFilter = authFilter;
        this.authenticationManager = authenticationManager;
        this.authenticationFailureCounter = authenticationFailureCounter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .headers(h -> {
                    h
                        .xssProtection(Customizer.withDefaults())
                        .contentSecurityPolicy(csp -> csp.policyDirectives(buildContentPolicyDirectives()));
                })
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((auth) -> {
                    auth
                            .requestMatchers("/auth/register/**", "/auth/login/begin", "/auth/csrf", "/auth/test")
                            .permitAll()
                            .anyRequest()
                            .hasRole("CLIENT");
                })
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                })
                .logout((logout) -> {
                    logout
                            .logoutUrl(AUTH_PATH + LOGOUT_PATH)
                            .logoutSuccessUrl(AUTH_PATH + LOGOUT_SUCCESS_PATH).permitAll()
                            .clearAuthentication(true)
                            .invalidateHttpSession(true)
                            .deleteCookies("SESSION");
                })
                .exceptionHandling((ex) -> ex.accessDeniedHandler(authenticationFailureCounter))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private String buildContentPolicyDirectives() {
        return "form-action 'self'; img-src 'self'; script-src 'self' child-src 'none'";
    }
}