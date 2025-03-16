package za.co.phumie.PostsService.mapper;

import za.co.phumie.PostsService.dto.CommentDto;
import za.co.phumie.PostsService.model.Comment;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto(comment.getCommentId(), comment.getContent(), comment.getAuthorUsername(), comment.getTimeStamp());
        return  dto;
    }
}
