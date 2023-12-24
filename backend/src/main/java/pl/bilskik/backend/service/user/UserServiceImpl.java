package pl.bilskik.backend.service.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import pl.bilskik.backend.data.dto.UserDTO;
import pl.bilskik.backend.data.dto.UserDetailsDTO;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.UserService;
import pl.bilskik.backend.service.auth.exception.UserException;
import pl.bilskik.backend.service.auth.exception.UsernameException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDTO getUser(String username) {
        if(username == null || username.isBlank()) {
            throw new UsernameException("Username is null!");
        }
        Optional<Users> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UserException("User is empty!");
        }
        return mapper.map(user.get(), UserDTO.class);

    }

    @Override
    public UserDetailsDTO getUserDetails(String username) {
        if(username == null || username.isBlank()) {
            throw new UsernameException("Username is null!");
        }
        Optional<Users> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UserException("User is empty!");
        }
        return mapper.map(user.get(), UserDetailsDTO.class);
    }
}
