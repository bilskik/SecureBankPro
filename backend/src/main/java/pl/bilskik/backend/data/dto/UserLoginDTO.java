package pl.bilskik.backend.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @NotBlank(message = "Username cannot be null or empty")
    private String username;
    @NotBlank(message = "Roles cannot be null or empty")
    private Set<String> roles;
    @NotBlank(message = "Passwords cannot be null or empty")
    private List<String> passwords;
    private boolean isLocked;
}
