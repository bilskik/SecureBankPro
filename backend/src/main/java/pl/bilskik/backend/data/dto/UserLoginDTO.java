package pl.bilskik.backend.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @NotBlank(message = "Username cannot be null or empty")
    private String username;
    @NotBlank(message = "Passwords cannot be null or empty")
    private List<String> passwords;
}
