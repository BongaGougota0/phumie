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
@Table(name = "phumie_users")
public class PhumieUser {

    @Id
    @Column("user_id")
    private Long userId;

    @Column("username")
    private String username;

    @Column("user_email")
    private String userEmail;

    @Column("password_hash")
    private String passwordHash;

    @Column("user_role")
    private UserRole userRole;

    @Column("about_user")
    private String aboutUser;

    @Column("avatar_url")
    private String avatarUrl;

    @Column("follower_count")
    private int followerCount = 0;

    @Column("following_count")
    private int followingCount = 0;

    @Column("active")          // renamed: avoids Lombok isIsActive() bug
    private boolean active = true;

    @CreatedDate               // must be on the field, not a method
    @Column("created_at")
    private LocalDateTime createdAt;
}