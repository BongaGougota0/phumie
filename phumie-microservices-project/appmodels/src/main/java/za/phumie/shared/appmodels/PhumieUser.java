package za.phumie.shared.appmodels;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Table
public class PhumieUser {
    @Id
    private Long userId;

    @Column
    private String username;

    @Column
    private String userEmail;

    private String passwordHash;
    private UserRole userRole;
    private String aboutUser;
    private String avatarUrl;

    // Denormalized counts — avoids COUNT(*) joins on every profile load
    @Column
    private int followerCount = 0;

    @Column
    private int followingCount = 0;

    @Column
    private boolean isActive = true;

    private LocalDateTime createdAt;

    @CreatedDate
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}