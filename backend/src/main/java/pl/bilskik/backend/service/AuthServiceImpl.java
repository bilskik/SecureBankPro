package pl.bilskik.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.dto.UserRegisterDTO;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.validator.ValidatorManager;
import pl.bilskik.backend.service.validator.enumeration.Entropy;
import pl.bilskik.backend.service.validator.exception.UserException;

import java.util.*;

import static pl.bilskik.backend.service.validator.enumeration.Entropy.GOOD;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidatorManager validator;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           ValidatorManager validator

    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        log.error(userRegisterDTO.toString());
        if(!validator.isValidUser(userRegisterDTO)) {
            throw new UserException("User is not valid!");
        }
        Entropy entropy = validator.countPasswordEntropy(userRegisterDTO.getUsername(),
                userRegisterDTO.getPassword());
       if(entropy == GOOD) {
//           List<Password> passwordList = createPasswordObjects(userRegisterDTO.getPassword());
           
           Users users = modelMapper.map(userRegisterDTO, Users.class);

       }
    }

    private List<Password> createPasswordObjects(String password) {
        List<String> passwords = createPasswords(password);
        List<String> salts =  createSalts();
        return null;
    }

    private List<String> createSalts() {
        return null;
    }
    @Override
    public void login(String username) {

    }

    @Override
    public void changePassword() {

    }

    private List<String> createPasswords(String password) {
        List<Character> chars = password.chars()
                .mapToObj(e -> (char) e).toList();

        List<String> passwordList = new ArrayList<>();
        int passLen = password.length();
        int partPassAmount = 20;
        for(int i=0; i<partPassAmount; i++) {
            Random rand = new Random();
            int partPassLen = generatePartPassLen(passLen);
            SortedSet<Integer> indexTs = new TreeSet<>();
            while(indexTs.size() != partPassLen) {
                int randomIndex = rand.nextInt(chars.size());
                indexTs.add((Integer) randomIndex);
            }
            String currPartialPassword = mapToPassword(indexTs, chars);
            passwordList.add(currPartialPassword);
        }
        return passwordList;
    }

    private String mapToPassword(SortedSet<Integer> indexList, List<Character> chars) {
        StringBuilder container = new StringBuilder();
        for(var i : indexList) {
            container.append(chars.get((i)));
        }
        return container.toString();
    }

    private int generatePartPassLen(int passLen) {
        int minPartPassLen = 6;
        int maxPartPassLen = countMaxPartPassLen(passLen);
        Random random = new Random();
        return random.nextInt(maxPartPassLen - minPartPassLen + 1) + minPartPassLen;
    }

    private int countMaxPartPassLen(int passLen) {
        if(passLen <= 14) {
            return passLen - 2;
        } else if(passLen <= 17) {
            return passLen - 4;
        } else {
            return passLen - 6;
        }
    }
}
