package za.co.phumie.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class PhumieUser {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String userEmail;
    private String password;
    private String aboutUser;
}
