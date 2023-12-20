package pl.bilskik.backend.service.auth.validator.constant;

import pl.bilskik.backend.service.auth.validator.exception.PasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher {
    public final static Pattern ONLY_LOWERCASE_PATTERN = Pattern.compile("^[a-z]+$");
    public final static Pattern ONLY_UPPERCASE_PATTERN = Pattern.compile("^[A-Z]+$");
    public final static Pattern ONLY_NUMBER_PATTERN = Pattern.compile("^[0-9]+$");
    public final static Pattern ONLY_LOWER_UPPER_PATTERN = Pattern.compile("^[a-zA-z]+$");
    public final static Pattern ONLY_LOWER_UPPER_NUMBER_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
    public final static Pattern ONLY_LOWER_UPPER_NUMBER_SPECIALCHAR_PATTERN = Pattern.compile("^.*$");
// only special characters
    public static int countLetterRange(String password) {
        Matcher numberCase = ONLY_NUMBER_PATTERN.matcher(password);
        Matcher lowerCase = ONLY_LOWERCASE_PATTERN.matcher(password);
        Matcher upperCase = ONLY_UPPERCASE_PATTERN.matcher(password);
        Matcher lowerUpperCase = ONLY_LOWER_UPPER_PATTERN.matcher(password);
        Matcher lowerUpperNumberCase = ONLY_LOWER_UPPER_NUMBER_PATTERN.matcher(password);
        Matcher allCase = ONLY_LOWER_UPPER_NUMBER_SPECIALCHAR_PATTERN.matcher(password);
        if(allCase.matches()) {
            return 94;
        } else if(lowerUpperNumberCase.matches()) {
            return 62;
        } else if(lowerUpperCase.matches()) {
            return 52;
        } else if(lowerCase.matches()) {
            return 26;
        } else if(upperCase.matches()) {
            return 26;
        } else if(numberCase.matches()) {
            return 10;
        } else {
            throw new PasswordException("I cannot counter letter range!");
        }
    }

}
