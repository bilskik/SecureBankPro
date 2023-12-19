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

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidatorManager validator;

    @Autowired
    public AuthService(UserRepository userRepository,
                       ModelMapper modelMapper,
                       ValidatorManager validator

    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        log.error(userRegisterDTO.toString());
        if(!validator.isValidUser(userRegisterDTO)) {
            throw new UserException("User is not valid!");
        }
        Entropy entropy = validator.countPasswordEntropy(userRegisterDTO.getUsername(), userRegisterDTO.getPassword());
        log.error(String.valueOf(entropy));
//        Users user = modelMapper.map(userRegisterDTO, Users.class);
//        if(user != null) {
//            List<Password> passwordList = createPasswords(user.getPassword());
//            userRepository.save(user);
//        }
    }

    public void login(String username) {
    }
    private List<Password> createPasswords(String password) {
        List<Password> passwordList = new ArrayList<>();
        int passLength = password.length();
        return null;
    }
}
