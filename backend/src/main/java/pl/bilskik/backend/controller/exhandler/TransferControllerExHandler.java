package pl.bilskik.backend.controller.exhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.bilskik.backend.controller.TransferController;
import pl.bilskik.backend.service.auth.exception.UserException;
import pl.bilskik.backend.service.auth.exception.UsernameException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(assignableTypes = { TransferController.class })
public class TransferControllerExHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationEx(MethodArgumentNotValidException ex) {
        Map<String, String> errMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errMap.put(err.getField(), err.getDefaultMessage());
        });
        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ UsernameException.class, UserException.class })
    public ResponseEntity<String> handleUsernameEx(UserException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
