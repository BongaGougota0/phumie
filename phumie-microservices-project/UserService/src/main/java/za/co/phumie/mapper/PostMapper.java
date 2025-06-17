package za.co.phumie.mapper;

import za.co.phumie.dto.PostDto;
import za.co.phumie.model.Post;

public class PostMapper {
    public static PostDto mapEntityToDto(Post entity) {
        PostDto dto = new PostDto(entity.getPostId(), entity.getAuthorUserId(),
                entity.getPostContent(), entity.getAuthorUsername(), entity.getTimeStamp());
        return dto;
    }

    public static Post mapDtoToEntity(PostDto dto) {
        Post entity = new Post();
        entity.setAuthorUsername(dto.postAuthor());
        entity.setAuthorUserId(dto.userId());
        entity.setPostContent(dto.postContent());
        entity.setTimeStamp(dto.postDate());
        return entity;
    }
}
