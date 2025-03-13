package za.co.phumie.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class PhumieUser {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String userEmail;
    private String password;
    private UserRole userRole;
    private String aboutUser;
}
