package pl.bilskik.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.repository.UserRepository;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(String username) {
//        List<String> rangeList = userRepository.findPasswordRangeByUsername(username);
//        if(rangeList == null || rangeList.isEmpty()) {
//            return "1-5"; //TO DO
//        } else {
//            return rangeList.get(0); //TO DO
//        }
        return "";
    }
}
