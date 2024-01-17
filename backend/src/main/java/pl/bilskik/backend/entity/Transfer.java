package pl.bilskik.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "transfer_tbl")
@Getter
@Setter
public class Transfer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private int transferId;
    @Column(name = "transfer_title")
    private String transferTitle;
    @Column(name = "sender_name")
    private String senderName;
    @Column(name = "sender_accno")
    private String senderAccNo;
    @Column(name = "receiver_name")
    private String receiverName;
    @Column(name = "receiver_accno")
    private String receiverAccNo;
    @Column(name = "amount")
    private long amount;
    @ManyToMany(
            mappedBy = "transferList",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    private List<Users> user; //0 fromUser //1 toUser

    public Transfer() {}

}

