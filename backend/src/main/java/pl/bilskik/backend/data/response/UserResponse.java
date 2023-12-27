package pl.bilskik.backend.data.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    @NotBlank(message = "AccountNo cannot be blank!")
    private String accountNo;
    @Min(value = 0, message = "Balance cannot be under 0!")
    private long balance;
}
