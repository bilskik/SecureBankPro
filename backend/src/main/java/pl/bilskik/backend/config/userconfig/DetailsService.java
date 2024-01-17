package pl.bilskik.backend.config.userconfig;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.data.dto.UserLoginDTO;
import pl.bilskik.backend.entity.Password;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.PasswordRepository;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.exception.PasswordException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DetailsService {

    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;
    private final ModelMapper modelMapper;

    public DetailsService(UserRepository userRepository,
                          PasswordRepository passwordRepository,
                          ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
        this.modelMapper = modelMapper;
    }

    public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException, PasswordException {
        Optional<Users> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found!");
        }
        List<Password> passwordObjList = passwordRepository.findPasswordByUser(user.get());
        if(passwordObjList == null || passwordObjList.isEmpty()) {
            throw new PasswordException("Password not found!");
        }
        UserLoginDTO userLoginDTO = mapToUserDTO(user.get(), passwordObjList);
        return new SecurityUser(userLoginDTO);
    }

    private UserLoginDTO mapToUserDTO(Users user, List<Password> passwordObjList) {
        UserLoginDTO userLoginDTO = modelMapper.map(user, UserLoginDTO.class);
        List<String> passwords = extractPasswords(passwordObjList);
        userLoginDTO.setPasswords(passwords);
        userLoginDTO.setRoles(user.getRoles());
        return userLoginDTO;
    }

    private List<String> extractPasswords(List<Password> passwordObjList) {
        List<String> passwords = new ArrayList<>();
        for(var passwordObj : passwordObjList) {
            passwords.add(passwordObj.getPassword());
        }
        return passwords;
    }
}
