package pl.bilskik.backend.data.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class UserDTO {
    @NotBlank(message = "AccountNo cannot be blank!")
    private String accountNo;
    @Min(value = 0, message = "Balance cannot be under 0!")
    private long balance;

    public UserDTO(String accountNo, long balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }
    public UserDTO() {}
}
