package pl.bilskik.backend.service.auth.passreset;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.PasswordRepository;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.auth.creator.PasswordCreator;
import pl.bilskik.backend.service.auth.validator.Entropy;
import pl.bilskik.backend.service.auth.validator.PasswordValidator;
import pl.bilskik.backend.service.exception.EntropyException;
import pl.bilskik.backend.service.exception.UserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthPasswordResetService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;
    private final PasswordValidator passwordValidator;
    private final PasswordCreator passwordCreator;

    public String beginResetPassword(String username, String email) {
        Optional<Users> user = userRepository.findByUsername(username);
        if(validateUser(user, email)) { //if user not valid exception will be thrown
            sendMail(); //here mail should be sent in order to authenticate user to reset password
        }
        return "Valid!";

    }

    public String finishResetPassword(String username, String email, String password) {
        Optional<Users> optionalUser = userRepository.findByUsername(username);
        if(validateUser(optionalUser,email)) {
            Users user = optionalUser.get();
            Entropy entropy = passwordValidator.countEntropy(username, password);
            if(entropy == Entropy.GOOD) {
                List<Password> passwordList = passwordCreator.createPasswords(password, user);
                replacePasswordsInDB(user, password, passwordList);
            } else {
                if(entropy == Entropy.REASONABLE) {
                    throw new EntropyException("Entropy is " + entropy.name() + " ,but still too weak. " +
                            "Improve your password strength!");
                }
                throw new EntropyException("Entropy is " + entropy.name() + " Improve your password strength!");
            }
        }
        return "Password changed!";
    }

    @Transactional
    private void replacePasswordsInDB(Users users, String password, List<Password> passwordList) {
        List<Password> oldPasswords = new ArrayList<>(users.getPasswordList());
        users.setPasswordList(passwordList);
        users.setPassword(passwordCreator.encodePassword(password));
        userRepository.save(users);
        passwordRepository.deleteAll(oldPasswords);
    }

    private boolean validateUser(Optional<Users> user, String email) {
        if(user.isEmpty()) {
            throw new UserException("Invalid credentials");
        }
        if(!user.get().getEmail().equals(email)) {
            throw new UserException("Invalid credentials");
        }
        return true;
    }

    private String sendMail() {
        return "Mail sended!";
    }

}
