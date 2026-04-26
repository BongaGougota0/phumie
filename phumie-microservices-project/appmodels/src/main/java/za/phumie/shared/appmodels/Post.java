package za.phumie.shared.appmodels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "posts",
        indexes = {
                @Index(name = "idx_post_author", columnList = "author_user_id"),
                @Index(name = "idx_post_created", columnList = "created_at DESC")
        }
)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private Long authorUserId;

    @Column(nullable = false)
    private String authorUsername;  // denormalized — avoids cross-service call on feed render

    private String textContent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostMediaType mediaType;

    private String imageUrl;

    // incremented by LikeService/PostService events
    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private int commentCount = 0;

    @Column(nullable = false)
    private boolean isDeleted = false;  // soft delete

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
