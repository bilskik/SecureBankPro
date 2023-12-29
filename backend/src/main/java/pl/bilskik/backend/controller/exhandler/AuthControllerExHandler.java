package pl.bilskik.backend.controller.exhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import pl.bilskik.backend.controller.AuthController;
import pl.bilskik.backend.service.auth.exception.EntropyException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(assignableTypes = { AuthController.class })
public class AuthControllerExHandler  {

    @ExceptionHandler({ MethodArgumentNotValidException.class } )
    public ResponseEntity<Map<String,String>> handleValidationEx(MethodArgumentNotValidException ex) {
        Map<String, String> errMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errMap.put(err.getField(), err.getDefaultMessage());
        });
        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ EntropyException.class } )
    public ResponseEntity<String> handleEntropyCountEx(EntropyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
