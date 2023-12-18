package pl.bilskik.backend.service.validator;

import org.springframework.stereotype.Component;
import pl.bilskik.backend.service.exception.PasswordException;
import pl.bilskik.backend.service.validator.constant.RegexMatcher;
import pl.bilskik.backend.service.validator.enumeration.Entropy;

import static pl.bilskik.backend.service.validator.enumeration.Entropy.*;

@Component
public class PasswordValidator {
    public boolean validatePassword(String password) {
        if(password == null || password.equals("")) {
            throw new PasswordException("Password is inValid! { Null or Empty }");
        }
        int letterRange = RegexMatcher.countLetterRange(password);
        Entropy entropy = countEntropy(password, letterRange);
        return true;

    }
    public Entropy countEntropy(String password, int letterRange) {
        double entropyValue = password.length()*log2(letterRange);
        //checking dictionary
        if(entropyValue > 75) {
            return GOOD;
        }
        else if(entropyValue > 50 && entropyValue < 75) {
            return REASONABLE;
        } else if(entropyValue < 50 && entropyValue > 25) {
            return POOR;
        }
        else if(entropyValue < 25 && entropyValue > 0){
            return WEAK;
        } else {
            throw new PasswordException("Cannot count valid entropy value!");
        }
    }
    private double log2(int val) {
        return Math.log(val) / Math.log(2);
    }
}
