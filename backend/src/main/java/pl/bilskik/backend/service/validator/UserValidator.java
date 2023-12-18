package pl.bilskik.backend.service.validator;

import org.springframework.stereotype.Component;
import pl.bilskik.backend.service.exception.UserException;

import static pl.bilskik.backend.service.validator.constant.UserConstants.CREDIT_CARD_NO_LENGTH;
import static pl.bilskik.backend.service.validator.constant.UserConstants.PESEL_LENGTH;

@Component
public class UserValidator {

    private final LoginValidator loginValidator;
    private final PasswordValidator passwordValidator;

    public UserValidator(LoginValidator loginValidator, PasswordValidator passwordValidator) {
        this.loginValidator = loginValidator;
        this.passwordValidator = passwordValidator;
    }

    public boolean validateUsername(String username) {
        return loginValidator.validateUsername(username);
    }
    public boolean validatePassword(String password) {
        return passwordValidator.validatePassword(password);
    }

    public boolean validatePesel(String pesel) {
        if(pesel == null || pesel.equals("")) {
            throw new UserException("Pesel is inValid! { Null or Empty }");
        }
        return pesel.length() == PESEL_LENGTH;
    }

    public boolean validateCreditCardNo(String creditCardNo) {
        if(creditCardNo == null || creditCardNo.equals("")) {
            throw new UserException("Pesel is inValid! { Null or Empty }");
        }
        return creditCardNo.length() == CREDIT_CARD_NO_LENGTH;
    }

}
