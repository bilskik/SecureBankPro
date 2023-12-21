package pl.bilskik.backend.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.data.dto.UserRegisterDTO;
import pl.bilskik.backend.service.AuthService;
import pl.bilskik.backend.service.auth.login.AuthLoginService;
import pl.bilskik.backend.service.auth.passchanger.AuthPasswordChangeService;
import pl.bilskik.backend.service.auth.register.AuthRegisterService;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthLoginService loginService;
    private final AuthRegisterService registerService;
    private final AuthPasswordChangeService passwordChangeService;

    @Autowired
    public AuthServiceImpl(AuthLoginService loginService,
                           AuthRegisterService registerService,
                           AuthPasswordChangeService passwordChangeService
                        ) {
        this.loginService = loginService;
        this.registerService = registerService;
        this.passwordChangeService = passwordChangeService;
    }

    @Override
    public String register(UserRegisterDTO userRegisterDTO) {
        return registerService.register(userRegisterDTO);
    }

    @Override
    public String beginLogin(String username) {
       return loginService.beginLogin(username);
    }
    @Override
    public String finishLogin(String username, String password) {
        return "";
    }

    @Override
    public String changePassword() {
        return "";
    }


}
