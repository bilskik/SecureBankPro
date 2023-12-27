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
        List<String> ranges = userRepository.findPasswordRangeByUsername(username);
        String range = "";
        if(ranges == null || ranges.isEmpty()) {
            range = createDummyRange();
        } else {
            range = chooseRange(ranges);
        }
        return range;
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
