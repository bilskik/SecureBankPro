package pl.bilskik.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import pl.bilskik.backend.dto.UserRegisterDTO;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.validator.UserValidator;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserValidator userValidator;

    @Autowired
    public AuthService(UserRepository userRepository,
                       ModelMapper modelMapper,
                       UserValidator userValidator

    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        validateUser(userRegisterDTO);
        Users user = modelMapper.map(userRegisterDTO, Users.class);
        if(user != null) {
            List<Password> passwordList = createPasswords(user.getPassword());
            userRepository.save(user);
        }
    }

    public void login(String username) {
    }
    private boolean validateUser(UserRegisterDTO userRegisterDTO) {
        return userValidator.validatePesel(userRegisterDTO.getPesel()) &&
                userValidator.validateCreditCardNo(userRegisterDTO.getCreditCardNo()) &&
                    userValidator.validateUsername(userRegisterDTO.getUsername()) &&
                        userValidator.validatePassword(userRegisterDTO.getPassword());

    }
    private List<Password> createPasswords(String password) {
        List<Password> passwordList = new ArrayList<>();
        int passLength = password.length();
        return null;
    }
}
