package pl.bilskik.backend.service.auth.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.PasswordRepository;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.auth.creator.PasswordCreator;
import pl.bilskik.backend.service.auth.exception.PasswordException;
import pl.bilskik.backend.service.auth.exception.UsernameException;

import java.util.*;

@Service
@Slf4j
public class AuthLoginService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;
    private final PasswordCreator passwordCreator;
    private final PasswordEncoder passwordEncoder;

    public AuthLoginService(UserRepository userRepository,
                            PasswordCreator passwordCreator,
                            PasswordRepository passwordRepository,
                            PasswordEncoder passwordEncoder
                            ) {
        this.userRepository = userRepository;
        this.passwordCreator = passwordCreator;
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public String beginLogin(String username) {
        if(username == null || username.equals("")) {
            throw new UsernameException("Username cannot be null!");
        }
        List<String> ranges = userRepository.findPasswordRangeByUsername(username);
        String range = "";
        if(ranges == null || ranges.isEmpty()) {
            range = createDummyRange();
        } else {
            range = chooseRange(ranges);
        }
        return range;
    }

    public String finishLogin(String username, String password) {
        if(username == null || username.equals("")) {
            throw new UsernameException("Username cannot be empty!");
        }
        if(password == null || password.equals("")) {
            throw new PasswordException("Password cannot be empty!");
        }
        Optional<Users> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UsernameException("Invalid identities!");
        }
        List<Password> passwordList = passwordRepository.findPasswordByUser(user.get());
        if(passwordList == null || passwordList.isEmpty()) {
            throw new UsernameException("Invalid identities");
        }
        boolean isFoundMatching = false;
        for(var pass : passwordList) {
            if (passwordEncoder.matches(password, pass.getPassword())) {
                isFoundMatching = true;
                break;
            }
        }
        if(!isFoundMatching) {
            throw new UsernameException("Invalid identities!");
        }
        return "Logged in";
     }


    private String chooseRange(List<String> ranges) {
        Random random = new Random();
        int passwordSelected = random.nextInt(ranges.size());
        return ranges.get(passwordSelected);
    }

    private String createDummyRange() {
        Random random = new Random();
        int dummyPassLen = random.nextInt(20) + 1;
        int maxPartLen = random.nextInt(14 - 12 + 1) + 12;
        int minPartLen = 6;
        int partPassLen = random.nextInt(maxPartLen - minPartLen + 1) + minPartLen;
        return passwordCreator.generateDummyRange(dummyPassLen, partPassLen);
    }


}
