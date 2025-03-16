package za.co.phumie.PostsService.mapper;

import za.co.phumie.PostsService.dto.PostDto;
import za.co.phumie.PostsService.model.Post;

public class PostMapper {
    public static PostDto mapEntityToDto(Post entity) {
        PostDto dto = new PostDto(entity.getPostId(), entity.getPostContent(), entity.getAuthorUsername(), entity.getTimeStamp());
        return dto;
    }

    public static Post mapDtoToEntity(PostDto dto) {
        Post entity = new Post();
        entity.setAuthorUsername(dto.postAuthor());
        entity.setPostContent(dto.postContent());
        entity.setTimeStamp(dto.postDate());
        return entity;
    }
}
