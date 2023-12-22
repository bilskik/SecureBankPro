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
import pl.bilskik.backend.security.filter.UserPassAuthFilter;
import pl.bilskik.backend.security.manager.AuthManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserPassAuthFilter userPassAuthFilter;
    private final AuthManager authenticationManager;
    @Autowired
    public SecurityConfig(UserPassAuthFilter userPassAuthFilter,
                          AuthManager authenticationManager) {
        this.userPassAuthFilter = userPassAuthFilter;
        this.authenticationManager = authenticationManager;
    }
    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)        //to change later -> its so insecure
//                .addFilterBefore(usernameFilter, UserPassAuthFilter.class)
                .addFilterBefore(userPassAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .authenticationManager()
                .authorizeHttpRequests((auth) -> {
                    auth
                            .requestMatchers("/auth/**")
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
