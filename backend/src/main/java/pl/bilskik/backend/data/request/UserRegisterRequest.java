package pl.bilskik.backend.data.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @Pattern(regexp = "^[a-zA-Z0-9]{9,}$", message = "Invalid username - at least 9 characters, " +
            "one lowercase, one uppercase, one digit")
    private String username;
    @NotBlank(message = "Password cannot be blank!")
    private String password;
    @Email(regexp = "^[a-zA-Z0-9.+_-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email is invalid!")
    @NotBlank(message = "Email cannot be blank!")
    private String email;
    @Pattern(regexp = "^\\d+${11}", message = "Invalid pesel!")
    private String pesel;
    @Pattern(regexp = "^\\d+${16}", message = "Invalid creditCardNo!")
    private String creditCardNo;

}
