package za.co.phumie.mapper;

import za.co.phumie.dto.PostDto;
import za.co.phumie.model.Post;
import java.util.stream.Collectors;

public class PostMapper {
    public static PostDto mapEntityToDto(Post entity) {
        PostDto dto = new PostDto(entity.getPostId(), entity.getAuthorUserId(),
                entity.getPostContent(), entity.getAuthorUsername(), entity.getTimeStamp());
        dto.setPostComments(entity.getComments().stream()
                .map(CommentMapper::toDto).collect(Collectors.toList()));
        return dto;
    }

    public static Post mapDtoToEntity(PostDto dto) {
        Post entity = new Post();
        entity.setAuthorUsername(dto.getPostAuthor());
        entity.setAuthorUserId(dto.getUserId());
        entity.setPostContent(dto.getPostContent());
        entity.setTimeStamp(dto.getPostDate());
        return entity;
    }
}
