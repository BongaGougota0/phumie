package za.co.phumie.dto;

import java.time.LocalDateTime;

public record CommentDto(String commentContent, String commentAuthor, LocalDateTime commentDate) {
}
