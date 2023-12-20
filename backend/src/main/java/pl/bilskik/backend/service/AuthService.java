package pl.bilskik.backend.service;


import pl.bilskik.backend.dto.UserRegisterDTO;

public interface AuthService {
    void register(UserRegisterDTO userRegisterDTO);
    void login(String username);
    void changePassword();
}
