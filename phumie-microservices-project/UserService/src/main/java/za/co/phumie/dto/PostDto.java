package za.co.phumie.dto;

import java.time.LocalDateTime;

public record PostDto(Long postId, Long userId, String postContent, String postAuthor, LocalDateTime postDate) {
}
