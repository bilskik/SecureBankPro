package pl.bilskik.backend.data.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinishResetPasswordRequest {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
