package pl.bilskik.backend.service.auth.password;

import org.springframework.stereotype.Service;

import java.util.Random;

import static pl.bilskik.backend.service.auth.password.PasswordConstraints.*;

@Service
public class PasswordLengthGenerator {
    private final Random random;

    public PasswordLengthGenerator() {
        this.random = new Random();
    }

    public int generatePartialPasswordLength(int MAX_PARTIAL_PASSWORD_LENGTH) {
        return random.nextInt(MAX_PARTIAL_PASSWORD_LENGTH - MIN_PARTIAL_PASSWORD_LENGTH + 1)
                + MIN_PARTIAL_PASSWORD_LENGTH;
    }

    public int calculateMaxPartialPasswordLength(int basePasswordLength) {
        int lowBound = 14;
        int middleBound = 17;

        //partial password length resolver(basePasswordLength won't be lower than 10-11 -> entropy won't allow that)
        if(basePasswordLength <= lowBound) {
            return basePasswordLength - 2;
        } else if(basePasswordLength <= middleBound) {
            return basePasswordLength - 4;
        } else {
            return basePasswordLength - 6;
        }
    }

    public int generateDummyBasePasswordLength() {
        return random.nextInt(MAX_BASE_PASSWORD_LENGTH - LOWER_BOUND_PASSWORD + 1) + LOWER_BOUND_PASSWORD;
    }

}
