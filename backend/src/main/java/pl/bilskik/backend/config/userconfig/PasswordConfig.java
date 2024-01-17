package pl.bilskik.backend.config.userconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    private final static int SALT_LENGTH = 16;
    private final static int HASH_LENGTH = 32;
    private final static int PARALLELISM = 1;
    private final static int MEMORY = 1 << 12;
    private final static int ITERATIONS = 3;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(
                SALT_LENGTH,
                HASH_LENGTH,
                PARALLELISM,
                MEMORY,
                ITERATIONS
        );
    }
}
