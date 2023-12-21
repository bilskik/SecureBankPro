package pl.bilskik.backend.data.dto;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    @Nullable
    private String passwordRange;
    public UserDTO(String username, String password, @Nullable String passwordRange) {
        this.username = username;
        this.password = password;
        this.passwordRange = passwordRange;
    }
}
