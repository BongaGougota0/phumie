package za.phumie.shared.appmodels;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Setter
@Getter
@Table
public class Comment {
    @Id
    private Long commentId;

    private Post post;

    @Column
    private Long authorUserId;

    @Column
    private String authorUsername;

    private String textContent;

    @Column
//    private CommentMediaType mediaType;
    private String mediaType;

    private String imageUrl;

    // Self-referencing for nested replies (null = top-level comment)
    private Comment parentComment;

//    @ToString.Exclude
//    private List<Comment> replies = new ArrayList<>();

    @Column
    private int likeCount = 0;

    @Column
    private boolean isDeleted = false;

    private LocalDateTime createdAt;

    @CreatedDate
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
