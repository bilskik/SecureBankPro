package pl.bilskik.backend.controller.exhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.bilskik.backend.controller.UserController;
import pl.bilskik.backend.service.exception.UserException;
import pl.bilskik.backend.service.exception.UsernameException;

@RestControllerAdvice(assignableTypes = { UserController.class })
public class UserControllerExHandler {

    @ExceptionHandler({UserException.class, UsernameException.class})
    public ResponseEntity<String> handleUserEx(UserException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
