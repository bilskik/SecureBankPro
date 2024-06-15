package pl.bilskik.backend.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.data.request.UserRegisterRequest;
import pl.bilskik.backend.service.AuthService;
import pl.bilskik.backend.service.auth.login.AuthLoginService;
import pl.bilskik.backend.service.auth.passreset.AuthPasswordResetService;
import pl.bilskik.backend.service.auth.register.AuthRegisterService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthLoginService loginService;
    private final AuthRegisterService registerService;
    private final AuthPasswordResetService passwordResetService;

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
