package za.co.phumie.PostsService.mapper;

import za.phumie.shared.appdtos.CommentDto;
import za.phumie.shared.appmodels.Comment;

import java.time.LocalDateTime;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto(comment.getTextContent(), comment.getAuthorUsername(), comment.getCreatedAt());
        return  dto;
    }

    public static Comment toEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setCreatedAt(LocalDateTime.now());
        comment.setTextContent(commentDto.commentContent());
        comment.setAuthorUsername(commentDto.commentAuthor());
        return comment;
    }
}
