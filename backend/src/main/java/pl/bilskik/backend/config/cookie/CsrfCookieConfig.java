package pl.bilskik.backend.config.cookie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class CsrfCookieConfig {

    @Value("${cookie.domain}")
    private String DOMAIN_URL;

    public CookieCsrfTokenRepository cookieCsrfTokenRepository() {
        var repo = CookieCsrfTokenRepository.withHttpOnlyFalse();
        repo.setCookieCustomizer((cookieBuilder) ->
                cookieBuilder
                        .sameSite(Cookie.SameSite.STRICT.attributeValue())
                        .secure(true)
                        .domain(DOMAIN_URL)
        );
        return repo;
    }
}
