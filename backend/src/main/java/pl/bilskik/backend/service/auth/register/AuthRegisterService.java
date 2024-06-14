package pl.bilskik.backend.service.auth.register;

import lombok.RequiredArgsConstructor;
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
import pl.bilskik.backend.service.auth.validator.Entropy;
import pl.bilskik.backend.service.auth.validator.PasswordValidator;
import pl.bilskik.backend.service.exception.EntropyException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static pl.bilskik.backend.service.auth.validator.Entropy.GOOD;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthRegisterService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordValidator validator;
    private final PasswordCreator passwordCreator;

    public String register(UserRegisterRequest userRegisterRequest) {
        Entropy entropy = validator.countEntropy(userRegisterRequest.getUsername(),
                userRegisterRequest.getPassword());
        if(entropy == GOOD) {
            Users user = mapToUsersObj(userRegisterRequest);
            List<Password> passwordList = passwordCreator.createPasswords(userRegisterRequest.getPassword(), user);
            Users buildedUser = buildUser(user, passwordList);
            userRepository.save(buildedUser);
        } else {
           throw new EntropyException("Entropy value: " + entropy + "is too weak!");
        }
        return "Account created!";
    }

    private Users mapToUsersObj(UserRegisterRequest userRegisterRequest) {
        Users users = modelMapper.map(userRegisterRequest, Users.class);
        users.setPassword(passwordCreator.encodePassword(userRegisterRequest.getPassword()));
        users.setBalance(1000);
        users.setAccountNo(RandomStringUtils.random(26, false, true));
        return users;
    }


    private Users buildUser(Users user, List<Password> passwordList) {
        user.setPasswordList(passwordList);
        Set<String> roles = new HashSet<>();
        roles.add("CLIENT");
        user.setRoles(roles);
        return user;
    }

}
