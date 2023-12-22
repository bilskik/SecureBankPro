package pl.bilskik.backend.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserLoginDTO {
    @NotBlank(message = "Username cannot be null or empty")
    private String username;
    @NotBlank(message = "Passwords cannot be null or empty")
    private List<String> passwords;

    public UserLoginDTO(String username, List<String> passwords) {
        this.username = username;
        this.passwords = passwords;
    }
    public UserLoginDTO() {}
}
