package pl.bilskik.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bilskik.backend.data.dto.UserRegisterDTO;
import pl.bilskik.backend.data.request.FirstLoginRequest;
import pl.bilskik.backend.data.request.LoginRequest;
import pl.bilskik.backend.data.response.AuthResponse;
import pl.bilskik.backend.data.response.FirstLoginResponse;
import pl.bilskik.backend.service.AuthService;
import pl.bilskik.backend.service.auth.AuthServiceImpl;

import static pl.bilskik.backend.controller.mapping.RequestPath.*;


@RestController
@CrossOrigin
@RequestMapping(value = AUTH_PATH)
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping(value = REGISTER_PATH)
    public ResponseEntity<AuthResponse> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(new AuthResponse(authService.register(userRegisterDTO)));
    }

    @PostMapping(value = LOGIN_BEGIN_PATH)
    public ResponseEntity<FirstLoginResponse> beginLogin(@RequestBody FirstLoginRequest request) {
        return ResponseEntity.ok(new FirstLoginResponse(authService.beginLogin(request.getUsername())));
    }
    @PostMapping(value = LOGIN_FINISH_PATH)
    public ResponseEntity<String> finishLogin() {
//        System.out.println(loginRequest.getPassword());
//        System.out.println(loginRequest.getUsername());

        return ResponseEntity.ok("OK");
    }
    @PostMapping(value = RESET_PASSWORD_PATH)
    public String resetPassword() {
        return "TO DO";
    }


}
