package pl.bilskik.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bilskik.backend.service.AuthService;


@RestController
@CrossOrigin
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/attemptLogin")
    public ResponseEntity<String> attemptLogin(@RequestBody String username) {
        authService.login(username);
        return ResponseEntity.ok("OK");
    }
}
