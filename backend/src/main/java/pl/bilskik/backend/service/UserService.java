package pl.bilskik.backend.service;

import org.springframework.stereotype.Service;
import pl.bilskik.backend.data.dto.UserDTO;
import pl.bilskik.backend.data.dto.UserDetailsDTO;

public interface UserService {
    UserDTO getUser(String username);

    UserDetailsDTO getUserDetails(String username);
}
