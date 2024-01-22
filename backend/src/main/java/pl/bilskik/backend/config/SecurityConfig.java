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
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.*;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pl.bilskik.backend.config.cookie.CsrfCookieConfig;
import pl.bilskik.backend.config.cors.CorsConfig;
import pl.bilskik.backend.config.failure.AuthenticationFailureCounter;
import pl.bilskik.backend.config.filter.AuthFilter;
import pl.bilskik.backend.config.manager.AuthManager;
import pl.bilskik.backend.config.userconfig.UserRole;

import java.util.Arrays;

import static pl.bilskik.backend.config.cookie.SessionCookieConfig.SESSION_COOKIE_NAME;
import static pl.bilskik.backend.controller.mapping.UrlMapping.*;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthFilter authFilter;
    private final CorsConfig corsConfig;
    private final CsrfCookieConfig csrfCookieConfig;
    private final AuthenticationFailureCounter authenticationFailureCounter;

    @Autowired
    public SecurityConfig(AuthFilter authFilter,
                          AuthenticationFailureCounter authenticationFailureCounter,
                          CorsConfig corsConfig,
                          CsrfCookieConfig csrfCookieConfig
    ) {
        this.authFilter = authFilter;
        this.authenticationFailureCounter = authenticationFailureCounter;
        this.corsConfig = corsConfig;
        this.csrfCookieConfig = csrfCookieConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(c -> c.configurationSource(corsConfig.corsConfigurationSource()))
                .csrf(csrf -> csrf.csrfTokenRepository(csrfCookieConfig.cookieCsrfTokenRepository()))
                .headers(h -> {
                    h
                        .xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                        .contentSecurityPolicy(csp -> csp.policyDirectives(buildContentPolicyDirectives()));
                })
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((auth) -> {
                    auth
                            .requestMatchers(
                                    REGISTER_URL,
                                    LOGIN_BEGIN_URL,
                                    CSRF_URL,
                                    RESET_PASSWORD_BEGIN_URL,
                                    RESET_PASSWORD_FINISH_URL
                            )
                            .permitAll()
                            .anyRequest()
                            .hasRole(UserRole.CLIENT.name());
                })
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                })
                .logout((logout) -> {
                    logout
                            .logoutUrl(LOGOUT_URL)
                            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                            .clearAuthentication(true)
                            .invalidateHttpSession(true)
                            .deleteCookies(SESSION_COOKIE_NAME);
                })
                .exceptionHandling((ex) -> ex.accessDeniedHandler(authenticationFailureCounter))
                .build();
    }

    private String buildContentPolicyDirectives() {
        return "form-action 'self'; img-src 'self'; script-src 'self' child-src 'none'";
    }
}