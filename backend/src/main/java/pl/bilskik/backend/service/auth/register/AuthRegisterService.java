package pl.bilskik.backend.service.auth.register;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.data.request.UserRegisterRequest;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.auth.password.PasswordCreator;
import pl.bilskik.backend.service.auth.validator.Entropy;
import pl.bilskik.backend.service.auth.validator.PasswordValidator;
import pl.bilskik.backend.service.exception.EntropyException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static pl.bilskik.backend.config.userconfig.UserRole.CLIENT;
import static pl.bilskik.backend.service.auth.validator.Entropy.GOOD;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthRegisterService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordValidator validator;
    private final PasswordCreator passwordCreator;
    private final PasswordEncoder passwordEncoder;

    public String register(UserRegisterRequest userRegisterRequest) {
        Entropy entropy = validator.countEntropy(userRegisterRequest.getUsername(),
                userRegisterRequest.getPassword());

        if(entropy == GOOD) {
            List<Password> passwordList = passwordCreator.createPartialPasswords(userRegisterRequest.getPassword());
            Users user = convertUserRegisterRequestToUser(userRegisterRequest, passwordList);
            userRepository.save(user);
        } else {
           throw new EntropyException("Entropy value: " + entropy + "is too weak!");
        }

        return "Account created!";
    }

    private Users convertUserRegisterRequestToUser(
            UserRegisterRequest userRegisterRequest,
            List<Password> passwordList
    ) {
        Users user = modelMapper.map(userRegisterRequest, Users.class);
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        user.setBalance(1000); //default balance (testing purposes)
        user.setAccountNo(RandomStringUtils.random(26, false, true));

        for(var p : passwordList) {
            p.setUser(user);
        }
        user.setPasswordList(passwordList);

        Set<String> roles = new HashSet<>();
        roles.add(CLIENT.name());
        user.setRoles(roles);

        return user;
    }
}
