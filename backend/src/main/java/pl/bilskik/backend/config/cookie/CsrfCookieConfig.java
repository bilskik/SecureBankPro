package pl.bilskik.backend.config.cookie;

import org.springframework.boot.web.server.Cookie;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class CsrfCookieConfig {

    public CookieCsrfTokenRepository cookieCsrfTokenRepository() {
        var repo = CookieCsrfTokenRepository.withHttpOnlyFalse();
        repo.setCookieCustomizer((cookieBuilder) -> cookieBuilder.sameSite(Cookie.SameSite.STRICT.attributeValue()));
        return repo;
    }
}
