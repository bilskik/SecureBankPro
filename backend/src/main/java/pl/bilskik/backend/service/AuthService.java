package pl.bilskik.backend.service;


import pl.bilskik.backend.data.dto.UserRegisterDTO;

public interface AuthService {
    String register(UserRegisterDTO userRegisterDTO);
    String beginLogin(String username);
    String finishLogin(String username, String password);
    String changePassword();
}
