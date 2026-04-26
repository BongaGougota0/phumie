package za.phumie.shared.appdtos;

import java.time.LocalDateTime;

public record CommentDto(String commentContent, String commentAuthor, LocalDateTime commentDate) {
}
