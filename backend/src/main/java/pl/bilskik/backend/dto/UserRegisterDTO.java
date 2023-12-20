package pl.bilskik.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {
    private String username;
    private String password;
    private String pesel;
    private String creditCardNo;
    public UserRegisterDTO() {}

    public UserRegisterDTO(String username, String password, String pesel, String creditCardNo) {
        this.username = username;
        this.password = password;
        this.pesel = pesel;
        this.creditCardNo = creditCardNo;
    }

    @Override
    public String toString() {
        return getUsername() + " " + getPassword() + " " + getPesel() + " " + getCreditCardNo();
    }
}
