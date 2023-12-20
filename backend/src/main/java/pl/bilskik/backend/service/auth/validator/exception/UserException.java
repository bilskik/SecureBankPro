package pl.bilskik.backend.service.auth.validator.exception;

import pl.bilskik.backend.dto.UserRegisterDTO;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
