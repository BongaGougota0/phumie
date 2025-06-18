package za.co.phumie.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto{

    Long postId;
    Long userId;
    String postContent;
    String postAuthor;
    LocalDateTime postDate;
    String postImage;

    public PostDto(Long postId, Long userId, String postContent,
                   String postAuthor, LocalDateTime postDate) {
        this.postId = postId;
        this.userId = userId;
        this.postContent = postContent;
        this.postAuthor = postAuthor;
        this.postDate = postDate;
    }

    List<CommentDto> postComments;
    Integer likesCount;
    Integer commentsCount;
    boolean like;
}
