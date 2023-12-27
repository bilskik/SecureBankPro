package pl.bilskik.backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bilskik.backend.data.request.UserRegisterRequest;
import pl.bilskik.backend.data.request.FirstLoginRequest;
import pl.bilskik.backend.data.response.FirstLoginResponse;
import pl.bilskik.backend.data.response.ResponseMessage;
import pl.bilskik.backend.service.AuthService;
import pl.bilskik.backend.service.auth.AuthServiceImpl;

import static pl.bilskik.backend.controller.mapping.UrlMapping.*;


@RestController
@RequestMapping(value = AUTH_PATH)
@CrossOrigin
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping(value = REGISTER_PATH)
    public ResponseEntity<ResponseMessage> register(
            @RequestBody @Valid UserRegisterRequest userRegisterRequest
    ) {
        return new ResponseEntity<>(
                new ResponseMessage(authService.register(userRegisterRequest)),
                HttpStatus.CREATED
        );
    }

    @PostMapping(value = LOGIN_BEGIN_PATH)
    public ResponseEntity<FirstLoginResponse> beginLogin(
            @RequestBody FirstLoginRequest request
    ) {
        return ResponseEntity.ok(new FirstLoginResponse(authService.beginLogin(request.getUsername())));
    }

    @PostMapping(value = LOGIN_FINISH_PATH)
    public ResponseEntity<ResponseMessage> finishLogin() {
        return ResponseEntity.ok(new ResponseMessage("Authenticated!"));
    }

    @PostMapping(value = RESET_PASSWORD_PATH)
    public String resetPassword() {
        return "TO DO";
    }

}
