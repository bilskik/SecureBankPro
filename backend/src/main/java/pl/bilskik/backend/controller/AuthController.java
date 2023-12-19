package pl.bilskik.backend.controller;

import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bilskik.backend.dto.UserRegisterDTO;
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

    @GetMapping("/register")
    public ResponseEntity<String> register() {
//        System.out.println(userRegisterDTO.getPassword());
//        authService.register(userRegisterDTO);
        logger.error("DZIALASZ!!!!>>??S?SA?D?ASD?AS?D?ASD?");
        return ResponseEntity.ok("Jest fine");
    }

    @PostMapping("/attemptLogin")
    public ResponseEntity<String> attemptLogin(@RequestBody String username) {
        authService.login(username);
        return ResponseEntity.ok("OK");
    }


}
