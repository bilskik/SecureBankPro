package pl.bilskik.backend.service.auth.validator.impl;

import org.springframework.stereotype.Component;
import pl.bilskik.backend.service.auth.exception.UsernameException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LoginValidator {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9\\_\\-\\.]{12,}$");

    public boolean validateUsername(String username) {
        if(username == null || username.equals("")) {
            throw new UsernameException("Username is inValid! { Null or Empty }");
        }
        if(!isValid(username)) {
            throw new UsernameException("Username is inValid!");
        }
        return true;
    }

    private boolean isValid(String username) {
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        return matcher.matches();
    }
}
