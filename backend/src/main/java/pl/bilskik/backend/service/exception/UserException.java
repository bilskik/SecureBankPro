package pl.bilskik.backend.service.auth.login.exception;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
