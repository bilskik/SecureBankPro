package pl.bilskik.backend.service.validator;

import pl.bilskik.backend.dto.UserRegisterDTO;
import pl.bilskik.backend.service.validator.enumeration.Entropy;

public interface ValidatorManager {
    boolean isValidUser(UserRegisterDTO userRegisterDTO);
    Entropy countPasswordEntropy(String username, String password);
}
