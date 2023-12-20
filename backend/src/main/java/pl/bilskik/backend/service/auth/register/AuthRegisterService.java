package pl.bilskik.backend.service.auth.register;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.dto.UserRegisterDTO;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.auth.creator.PasswordCreator;
import pl.bilskik.backend.service.auth.validator.ValidatorManager;
import pl.bilskik.backend.service.auth.validator.enumeration.Entropy;
import pl.bilskik.backend.service.auth.validator.exception.UserException;

import java.util.*;

import static pl.bilskik.backend.service.auth.validator.enumeration.Entropy.GOOD;

@Service
@Slf4j
public class AuthRegisterService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidatorManager validator;
    private final PasswordCreator passwordCreator;

    @Autowired
    public AuthRegisterService(
            UserRepository userRepository,
            ModelMapper modelMapper,
            ValidatorManager validator,
            PasswordCreator passwordCreator
    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.passwordCreator = passwordCreator;
    }
    public void register(UserRegisterDTO userRegisterDTO) {
        log.error(userRegisterDTO.toString());
        if(!validator.isValidUser(userRegisterDTO)) {
            throw new UserException("User is not valid!");
        }
        Entropy entropy = validator.countPasswordEntropy(userRegisterDTO.getUsername(),
                userRegisterDTO.getPassword());
        if(entropy == GOOD) {
           List<Password> passwordList = passwordCreator.createPasswords(userRegisterDTO.getPassword());
            Users users = modelMapper.map(userRegisterDTO, Users.class);

        }
    }

}
