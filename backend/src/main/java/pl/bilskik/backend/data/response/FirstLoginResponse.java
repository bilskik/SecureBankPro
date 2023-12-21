package pl.bilskik.backend.data.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirstLoginResponse {
    private String range;
    public FirstLoginResponse(String range) {
        this.range = range;
    }
}
