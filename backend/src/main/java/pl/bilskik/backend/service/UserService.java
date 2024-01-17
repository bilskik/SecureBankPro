package pl.bilskik.backend.service;

import pl.bilskik.backend.data.response.UserResponse;
import pl.bilskik.backend.data.response.UserDetailsResponse;

public interface UserService {
    UserResponse getUser(String username);

    UserDetailsResponse getUserDetails(String username);
}
