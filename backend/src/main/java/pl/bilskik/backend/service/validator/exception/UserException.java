package pl.bilskik.backend.service.validator.exception;

import pl.bilskik.backend.dto.UserRegisterDTO;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
