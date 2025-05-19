package za.co.phumie.service.usersint;

import za.co.phumie.dto.ResponseDto;
import za.co.phumie.model.Comment;

@FunctionalInterface
public interface IComment {
    ResponseDto postComment(Comment comment);
}
