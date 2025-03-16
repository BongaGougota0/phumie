package za.co.phumie.PostsService.dto;

import java.time.LocalDateTime;

public record CommentDto(Long commentId, String commentContent, String commentAuthor, LocalDateTime commentDate) {
}
