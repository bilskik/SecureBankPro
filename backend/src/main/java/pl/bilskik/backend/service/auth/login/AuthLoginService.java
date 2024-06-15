package pl.bilskik.backend.service.auth.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.auth.password.DummyPasswordGenerator;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthLoginService {

    private final UserRepository userRepository;
    private final DummyPasswordGenerator dummyPasswordGenerator;

    public String beginLogin(String username) {
        List<String> ranges = userRepository.findPasswordRangeByUsername(username);
        String range = "";
        if(ranges == null || ranges.isEmpty()) {
            range = dummyPasswordGenerator.generateDummyRange();
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

}
