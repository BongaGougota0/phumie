package za.phumie.shared.appmodels;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Table
public class Post {
    @Id
    private Long postId;

    @Column
    private Long authorUserId;

    @Column
    private String authorUsername;  // denormalized — avoids cross-service call on feed render

    private String textContent;

    @Column
//    private PostMediaType mediaType;
    private String mediaType;

    private String imageUrl;

    // incremented by LikeService/PostService events
    @Column
    private int likeCount = 0;

    @Column
    private int commentCount = 0;

    @Column
    private boolean isDeleted = false;  // soft delete

    private LocalDateTime createdAt;

//    @ToString.Exclude
//    private List<Comment> comments = new ArrayList<>();

    @CreatedDate
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}
