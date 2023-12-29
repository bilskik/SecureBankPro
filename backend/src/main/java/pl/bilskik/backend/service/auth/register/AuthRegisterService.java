package pl.bilskik.backend.service.auth.register;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.data.request.UserRegisterRequest;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.auth.creator.PasswordCreator;
import pl.bilskik.backend.service.auth.exception.EntropyException;
import pl.bilskik.backend.service.auth.validator.Entropy;
import pl.bilskik.backend.service.auth.validator.PasswordValidator;

import java.util.*;

import static pl.bilskik.backend.service.auth.validator.Entropy.GOOD;

@Service
@Slf4j
public class AuthRegisterService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordValidator validator;
    private final PasswordCreator passwordCreator;

    @Autowired
    public AuthRegisterService(
                UserRepository userRepository,
                ModelMapper modelMapper,
                PasswordValidator validator,
                PasswordCreator passwordCreator
        ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.passwordCreator = passwordCreator;
    }
    public String register(UserRegisterRequest userRegisterRequest) {
        Entropy entropy = validator.countEntropy(userRegisterRequest.getUsername(),
                userRegisterRequest.getPassword());
        if(entropy == GOOD) {
            Users user = mapToUsersObj(userRegisterRequest);
            List<Password> passwordList = createPasswords(userRegisterRequest.getPassword(), user);
            user.setPasswordList(passwordList);
            userRepository.save(user);
        } else {
           throw new EntropyException("Entropy value: " + entropy + "is too weak!");
        }
        return "Account created!";
    }

    private Users mapToUsersObj(UserRegisterRequest userRegisterRequest) {
        Users users = modelMapper.map(userRegisterRequest, Users.class);
        users.setPassword(passwordCreator.encodePassword(userRegisterRequest.getPassword()));
        users.setAccountNo(RandomStringUtils.random(26, false, true));
        return users;
    }

    private List<Password> createPasswords(String password, Users users) {
        List<Password> passwordList = passwordCreator.createPasswords(password);
        for(var p : passwordList) {
            p.setUser(users);
        }
        return passwordList;
    }

}
