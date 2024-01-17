package pl.bilskik.backend.service;


import pl.bilskik.backend.data.request.UserRegisterRequest;

public interface AuthService {
    String register(UserRegisterRequest userRegisterRequest);
    String beginLogin(String username);
    String beginResetPassword(String username, String email);
    String finishResetPassword(String username, String email, String password);
}
