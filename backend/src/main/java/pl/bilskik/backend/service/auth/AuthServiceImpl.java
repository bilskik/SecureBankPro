package pl.bilskik.backend.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.data.request.UserRegisterRequest;
import pl.bilskik.backend.service.AuthService;
import pl.bilskik.backend.service.auth.login.AuthLoginService;
import pl.bilskik.backend.service.auth.passreset.AuthPasswordResetService;
import pl.bilskik.backend.service.auth.register.AuthRegisterService;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthLoginService loginService;
    private final AuthRegisterService registerService;
    private final AuthPasswordResetService passwordResetService;

    @Autowired
    public AuthServiceImpl(AuthLoginService loginService,
                           AuthRegisterService registerService,
                           AuthPasswordResetService passwordResetService
                        ) {
        this.loginService = loginService;
        this.registerService = registerService;
        this.passwordResetService = passwordResetService;
    }

    @Override
    public String register(UserRegisterRequest userRegisterRequest) {
        return registerService.register(userRegisterRequest);
    }

    @Override
    public String beginLogin(String username) {
        return loginService.beginLogin(username);
    }

    @Override
    public String beginResetPassword(String username, String email) {
        return passwordResetService.beginResetPassword(username, email);
    }

    @Override
    public String finishResetPassword(String username, String email, String password) {
        return passwordResetService.finishResetPassword(username, email, password);
    }

}
