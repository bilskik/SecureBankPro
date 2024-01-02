package pl.bilskik.backend.config.failure;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginAttemptsContainer {

    private Map<String, Integer> loginAttemptsHolder;

    public LoginAttemptsContainer() {
        this.loginAttemptsHolder = new HashMap<>();
    }

    public void commitChanges(Map<String, Integer> toUpdateHolder) {
        this.loginAttemptsHolder = toUpdateHolder;
    }

    public Map<String, Integer> getLoginAttemptsHolder() {
        return loginAttemptsHolder;
    }

}
