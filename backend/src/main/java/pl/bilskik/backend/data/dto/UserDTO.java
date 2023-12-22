package pl.bilskik.backend.data.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class UserDTO {
//    @NotBlank(message = "Username cannot be null or empty")
    private String username;
//    @NotBlank(message = "Password cannot be null or empty")
    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public UserDTO() {}
}
