package pl.bilskik.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import pl.bilskik.backend.data.request.BeginResetPasswordRequest;
import pl.bilskik.backend.data.request.FinishResetPasswordRequest;
import pl.bilskik.backend.data.request.FirstLoginRequest;
import pl.bilskik.backend.data.request.UserRegisterRequest;
import pl.bilskik.backend.data.response.FirstLoginResponse;
import pl.bilskik.backend.data.response.ResponseMessage;
import pl.bilskik.backend.service.AuthService;

import static pl.bilskik.backend.controller.mapping.UrlMapping.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = AUTH_PATH)
public class AuthController {

    private final AuthService authService;

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
            @RequestBody @Valid FirstLoginRequest request
    ) {
        return ResponseEntity.ok(new FirstLoginResponse(authService.beginLogin(request.getUsername())));
    }

    @PostMapping(value = LOGIN_FINISH_PATH)
    public ResponseEntity<ResponseMessage> finishLogin() {  //filters handles login finish
        return ResponseEntity.ok(new ResponseMessage("Authenticated!"));
    }

    @PostMapping(value = RESET_PASSWORD_BEGIN_PATH)
    public ResponseEntity<ResponseMessage> beginResetPassword(
            @RequestBody @Valid BeginResetPasswordRequest request
    ) {
        return ResponseEntity.ok(
                new ResponseMessage(
                        authService.beginResetPassword(request.getUsername(), request.getEmail())
                )
        );
    }

    @PostMapping(value = RESET_PASSWORD_FINISH_PATH)
    public ResponseEntity<ResponseMessage> finishResetPassword(
            @RequestBody @Valid FinishResetPasswordRequest request
    ) {
        return ResponseEntity.ok(
                new ResponseMessage(
                        authService.finishResetPassword(
                            request.getUsername(),
                            request.getEmail(),
                            request.getPassword()
                        )
                )
        );
    }

    @GetMapping(value = LOGOUT_SUCCESS_PATH)
    public ResponseEntity<ResponseMessage> success() {
        return ResponseEntity.ok(new ResponseMessage("Logout was succesful!"));
    }

    @GetMapping
    public ResponseEntity<ResponseMessage> getAuth() {
        return ResponseEntity.ok(new ResponseMessage("Authenticated!"));
    }

    @GetMapping(value = CSRF_PATH)
    public CsrfToken csrfToken(CsrfToken token) {
        return token;
    }
}
