package pl.bilskik.backend.data.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirstLoginRequest {
    @NotBlank(message = "Username cannot be blank!")
    private String username;
}
