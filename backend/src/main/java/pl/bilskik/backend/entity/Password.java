package pl.bilskik.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "password_tbl")
@Getter
@Setter
public class Password {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "password_id")
    private int passwordId;
    @Column(name = "ranges")
    private String ranges;
    @Column(name = "password")
    private String password;
    @ManyToOne
    private Users user;

    public Password(String ranges, String password) {
        this.ranges = ranges;
        this.password = password;
    }
}
