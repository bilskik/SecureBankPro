package pl.bilskik.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.bilskik.backend.security.filter.AuthFilter;
import pl.bilskik.backend.security.manager.AuthManager;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthFilter authFilter;
    private final AuthManager authenticationManager;
    @Autowired
    public SecurityConfig(AuthFilter authFilter,
                          AuthManager authenticationManager) {
        this.authFilter = authFilter;
        this.authenticationManager = authenticationManager;
    }
    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)        //to change later -> its so insecure
                .addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((auth) -> {
                    auth
                            .requestMatchers("/auth/register/**", "/auth/login/begin")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                })
                .build();
    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("http://localhost:3000");
//        config.setAllowedMethods(List.of("GET", "POST", "PUT","DELETE"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
}
