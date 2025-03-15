package za.co.phumie.PostsService.dto;

public record CommentDto(Long commentId, String commentContent, String commentAuthor, String commentDate) {
}
