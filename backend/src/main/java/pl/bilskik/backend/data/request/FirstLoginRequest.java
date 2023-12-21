package pl.bilskik.backend.data.request;

import lombok.Getter;
import lombok.Setter;
import pl.bilskik.backend.data.response.FirstLoginResponse;

@Getter
@Setter
public class FirstLoginRequest {
    private String username;
    public FirstLoginRequest(String username) {
        this.username = username;
    }
    public FirstLoginRequest() {}
}
