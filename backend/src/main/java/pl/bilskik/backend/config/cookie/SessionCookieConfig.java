package pl.bilskik.backend.config.cookie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SessionCookieConfig {

    public final static String SESSION_COOKIE_NAME = "SESSION";

    @Value("${cookie.domain}")
    public String SESSION_COOKIE_DOMAIN;

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(SESSION_COOKIE_NAME);
        serializer.setSameSite(Cookie.SameSite.STRICT.attributeValue());
        serializer.setUseSecureCookie(true);
        serializer.setUseHttpOnlyCookie(true);
        serializer.setCookiePath("/");
        serializer.setDomainName(SESSION_COOKIE_DOMAIN);
        return serializer;
    }
}
