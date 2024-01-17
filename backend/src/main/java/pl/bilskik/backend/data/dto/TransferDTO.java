package pl.bilskik.backend.data.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {

    @NotBlank(message = "Invalid transferTitle!")
    private String transferTitle;
    @NotBlank(message = "Invalid sender name!")
    private String senderName;
    @Pattern(regexp = "^\\w{26}$", message = "Sender account number is invalid!")
    private String senderAccNo;
    @NotBlank(message = "Invalid receiver name!")
    private String receiverName;
    @Pattern(regexp = "^\\w{26}$", message = "Receiver account number is invalid!")
    private String receiverAccNo;
    @Min(value = 1, message = "Amount of money to send must be at least 1!")
    private long amount;

}
