package pl.bilskik.backend.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsDTO {
    private String creditCardNo;
    private String pesel;

    public UserDetailsDTO(String creditCardNo, String pesel) {
        this.creditCardNo = creditCardNo;
        this.pesel = pesel;
    }
    public UserDetailsDTO() {}
}
