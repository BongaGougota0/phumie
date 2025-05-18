package za.co.phumie.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Follower> followers = new ArrayList<>();

    @Override
    public String toString() {
        return "PhumieUser(userId=" + userId +
                ", username=" + username +
                ", userEmail=" + userEmail +
                ", userRole=" + userRole +
                ", aboutUser=" + aboutUser + ")";
    }
}
