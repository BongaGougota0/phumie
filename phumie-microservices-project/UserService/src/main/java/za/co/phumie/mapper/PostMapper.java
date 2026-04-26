package za.co.phumie.mapper;

import za.phumie.shared.appdtos.PostDto;
import za.phumie.shared.appmodels.Post;

public class PostMapper {
    public static PostDto mapEntityToDto(Post entity) {
        PostDto dto = new PostDto(entity.getPostId(), entity.getAuthorUserId(), entity.getTextContent(), entity.getAuthorUsername(), entity.getCreatedAt());
        return dto;
    }

    public static Post mapDtoToEntity(PostDto dto) {
        Post entity = new Post();
        entity.setAuthorUsername(dto.postAuthor());
        entity.setAuthorUserId(dto.userId());
        entity.setTextContent(dto.postContent());
        entity.setCreatedAt(dto.postDate());
        return entity;
    }
}
