package pl.bilskik.backend.service.auth.validator.impl;
import org.springframework.stereotype.Component;
import pl.bilskik.backend.data.dto.UserRegisterDTO;
import pl.bilskik.backend.service.auth.validator.constant.UserConstants;
import pl.bilskik.backend.service.auth.validator.enumeration.Entropy;
import pl.bilskik.backend.service.auth.exception.UserException;
import pl.bilskik.backend.service.auth.validator.ValidatorManager;

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
        return pesel.length() == UserConstants.PESEL_LENGTH;
    }

    private boolean validateCreditCardNo(String creditCardNo) {
        if(creditCardNo == null || creditCardNo.equals("")) {
            throw new UserException("Pesel is inValid! { Null or Empty }");
        }
        return creditCardNo.length() == UserConstants.CREDIT_CARD_NO_LENGTH;
    }
}
