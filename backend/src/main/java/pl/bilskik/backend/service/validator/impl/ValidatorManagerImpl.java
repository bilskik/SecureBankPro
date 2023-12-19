package pl.bilskik.backend.service.validator.impl;
import org.springframework.stereotype.Component;
import pl.bilskik.backend.dto.UserRegisterDTO;
import pl.bilskik.backend.service.validator.exception.UserException;
import pl.bilskik.backend.service.validator.ValidatorManager;
import pl.bilskik.backend.service.validator.enumeration.Entropy;

import static pl.bilskik.backend.service.validator.constant.UserConstants.CREDIT_CARD_NO_LENGTH;
import static pl.bilskik.backend.service.validator.constant.UserConstants.PESEL_LENGTH;

@Component
public class ValidatorManagerImpl implements ValidatorManager {

    private final LoginValidator loginValidator;
    private final PasswordValidator passwordValidator;

    public ValidatorManagerImpl(LoginValidator loginValidator, PasswordValidator passwordValidator) {
        this.loginValidator = loginValidator;
        this.passwordValidator = passwordValidator;
    }

    @Override
    public boolean isValidUser(UserRegisterDTO userRegisterDTO) {
        return validateUsername(userRegisterDTO.getUsername()) &&
                validatePassword(userRegisterDTO.getUsername(), userRegisterDTO.getPassword()) &&
                validatePesel(userRegisterDTO.getPesel()) &&
                validateCreditCardNo(userRegisterDTO.getCreditCardNo());
    }

    @Override
    public Entropy countPasswordEntropy(String username, String password) {
        return passwordValidator.countEntropy(username, password);
    }

    private boolean validateUsername(String username) {
        return loginValidator.validateUsername(username);
    }
    private boolean validatePassword(String username, String password) {
        return passwordValidator.validatePassword(username, password);
    }

    private boolean validatePesel(String pesel) {
        if(pesel == null || pesel.equals("")) {
            throw new UserException("Pesel is inValid! { Null or Empty }");
        }
        return pesel.length() == PESEL_LENGTH;
    }

    private boolean validateCreditCardNo(String creditCardNo) {
        if(creditCardNo == null || creditCardNo.equals("")) {
            throw new UserException("Pesel is inValid! { Null or Empty }");
        }
        return creditCardNo.length() == CREDIT_CARD_NO_LENGTH;
    }
}
