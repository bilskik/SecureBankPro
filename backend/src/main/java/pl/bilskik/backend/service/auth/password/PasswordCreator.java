package pl.bilskik.backend.service.auth.password;

import pl.bilskik.backend.entity.Password;

import java.util.List;

public interface PasswordCreator {

    List<Password> createPartialPasswords(String password);

}
