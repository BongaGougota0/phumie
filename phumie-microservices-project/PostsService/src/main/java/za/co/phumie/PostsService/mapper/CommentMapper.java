package za.co.phumie.PostsService.mapper;

import za.co.phumie.PostsService.dto.CommentDto;
import za.co.phumie.PostsService.model.Comment;
import java.time.LocalDateTime;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto(comment.getContent(), comment.getAuthorUsername(), comment.getTimeStamp());
        return  dto;
    }

    public static Comment toEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setTimeStamp(LocalDateTime.now());
        comment.setContent(commentDto.commentContent());
        comment.setAuthorUsername(commentDto.commentAuthor());
        return comment;
    }
}
