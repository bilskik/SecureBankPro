package pl.bilskik.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import pl.bilskik.backend.security.filter.UserPassAuthFilter;
import pl.bilskik.backend.security.filter.UsernameFilter;
import pl.bilskik.backend.security.manager.AuthManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserPassAuthFilter userPassAuthFilter;
    private final UsernameFilter usernameFilter;
    private final AuthManager authenticationManager;
    @Autowired
    public SecurityConfig(UserPassAuthFilter userPassAuthFilter,
                          UsernameFilter usernameFilter,
                          AuthManager authenticationManager) {
        this.userPassAuthFilter = userPassAuthFilter;
        this.usernameFilter = usernameFilter;
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
                            .requestMatchers("/login", "/register")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
//                .sessionManagement((session) -> {
//                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
//                })
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
