package pl.bilskik.backend.service.auth.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.repository.PasswordRepository;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.auth.creator.PasswordCreator;

import java.util.List;
import java.util.Random;

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
        String chosenRange = "";
        if(ranges == null || ranges.isEmpty()) {
            chosenRange = createDummyRange();
        } else {
            chosenRange = chooseRange(ranges);
        }
        return chosenRange;
    }
    
    private String chooseRange(List<String> ranges) {
        Random random = new Random();
        int passwordSelected = random.nextInt(ranges.size());
        return ranges.get(passwordSelected);
    }

    private String createDummyRange() {
        Random random = new Random();
        int passLen = passwordCreator.generateDummyPassLen();
        int partPassLen = passwordCreator.generatePartPassLen(passLen);
        return passwordCreator.generateDummyRange(passLen, partPassLen);
    }
}
