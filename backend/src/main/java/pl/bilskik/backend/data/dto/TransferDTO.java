package pl.bilskik.backend.data.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class TransferDTO {

    @NotBlank(message = "Invalid transferTitle!")
    private String transferTitle;
    @NotBlank(message = "Invalid sender name!")
    private String senderName;
    @Size(min = 20, max = 20, message = "Account number size must be equals to 20!")
    private String senderAccNo;
    @NotBlank(message = "Invalid receiver name!")
    private String receiverName;
    @Size(min = 20, max = 20,  message = "Account number size must be equals to 20!")
    private String receiverAccNo;
    @Min(value = 1, message = "Amount of money to send must be at least 1!")
    private long amount;

    public TransferDTO(String transferTitle,
                       String senderName,
                       String senderAccNo,
                       String receiverName,
                       String receiverAccNo,
                       long amount) {
        this.transferTitle = transferTitle;
        this.senderName = senderName;
        this.senderAccNo = senderAccNo;
        this.receiverName = receiverName;
        this.receiverAccNo = receiverAccNo;
        this.amount = amount;
    }

    public TransferDTO() {}
}
