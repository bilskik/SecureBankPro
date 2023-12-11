package pl.bilskik.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transfer")
@Getter
@Setter
public class Transfer {
    @Id
    @Column(name = "transfer_id")
    private Long transferId;
    @Column(name = "transfer_title")
    private String transferTitle;
    @Column(name = "receiver_name")
    private String receiverName;
    @Column(name = "amount")
    private Long amount;
    @ManyToOne
    private User fromUser;
    @ManyToOne
    private User toUser;

    public Transfer() {

    }

    public Transfer(Long transferId, String transferTitle, String receiverName, Long amount, User fromUser, User toUser) {
        this.transferId = transferId;
        this.transferTitle = transferTitle;
        this.receiverName = receiverName;
        this.amount = amount;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}

