package pl.bilskik.backend.service.auth.validator;

import pl.bilskik.backend.data.dto.UserRegisterDTO;
import pl.bilskik.backend.service.auth.validator.enumeration.Entropy;

public interface ValidatorManager {
    boolean isValidUser(UserRegisterDTO userRegisterDTO);
    Entropy countPasswordEntropy(String username, String password);
}
