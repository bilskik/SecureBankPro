package pl.bilskik.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.dto.UserRegisterDTO;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.validator.ValidatorManager;
import pl.bilskik.backend.service.validator.exception.UserException;

import java.util.ArrayList;
import java.util.List;

@Service
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
        if(!validator.isValidUser(userRegisterDTO)) {
            throw new UserException("User is not valid!");
        }
        Users user = modelMapper.map(userRegisterDTO, Users.class);
        if(user != null) {
            List<Password> passwordList = createPasswords(user.getPassword());
            userRepository.save(user);
        }
    }

    public void login(String username) {
    }
    private List<Password> createPasswords(String password) {
        List<Password> passwordList = new ArrayList<>();
        int passLength = password.length();
        return null;
    }
}
