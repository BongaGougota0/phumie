package za.co.phumie.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "followers")
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long subjectUserId;
    private String followerUsername;
    private Long followerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private PhumieUser user;
}
