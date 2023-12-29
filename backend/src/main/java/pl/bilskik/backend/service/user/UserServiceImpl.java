package pl.bilskik.backend.service.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bilskik.backend.data.response.UserResponse;
import pl.bilskik.backend.data.response.UserDetailsResponse;
import pl.bilskik.backend.entity.Users;
import pl.bilskik.backend.repository.UserRepository;
import pl.bilskik.backend.service.UserService;
import pl.bilskik.backend.service.exception.UserException;
import pl.bilskik.backend.service.exception.UsernameException;

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
    public UserResponse getUser(String username) {
        if(username == null || username.isBlank()) {
            throw new UsernameException("Username is null!");
        }
        Optional<Users> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UserException("User is empty!");
        }
        System.out.println("USER");
        return mapper.map(user.get(), UserResponse.class);

    }

    @Override
    public UserDetailsResponse getUserDetails(String username) {
        if(username == null || username.isBlank()) {
            throw new UsernameException("Username is null!");
        }
        Optional<Users> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new UserException("User is empty!");
        }
        return mapper.map(user.get(), UserDetailsResponse.class);
    }
}
