package za.co.phumie.PostsService.service.postsInt;

import za.co.phumie.PostsService.dto.ResponseDto;
import za.co.phumie.PostsService.model.Comment;

@FunctionalInterface
public interface IComments {
    ResponseDto postComment(Comment comment);
}
