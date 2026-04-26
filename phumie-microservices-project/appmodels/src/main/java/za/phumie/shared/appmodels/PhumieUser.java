package za.phumie.shared.appmodels;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "phumie_users")
public class PhumieUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String userEmail;

    private String passwordHash;
    private UserRole userRole;
    private String aboutUser;
    private String avatarUrl;

    // Denormalized counts — avoids COUNT(*) joins on every profile load
    @Column(nullable = false)
    private int followerCount = 0;

    @Column(nullable = false)
    private int followingCount = 0;

    @Column(nullable = false)
    private boolean isActive = true;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}