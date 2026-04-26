package za.phumie.shared.appmodels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Table(name = "comments",
        indexes = {
                @Index(name = "idx_comment_post", columnList = "post_id"),
                @Index(name = "idx_comment_parent", columnList = "parent_comment_id"),
                @Index(name = "idx_comment_author", columnList = "author_user_id")
        }
)
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private Long authorUserId;

    @Column(nullable = false)
    private String authorUsername;

    private String textContent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentMediaType mediaType;

    private String imageUrl;

    // Self-referencing for nested replies (null = top-level comment)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Comment> replies = new ArrayList<>();

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private boolean isDeleted = false;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
