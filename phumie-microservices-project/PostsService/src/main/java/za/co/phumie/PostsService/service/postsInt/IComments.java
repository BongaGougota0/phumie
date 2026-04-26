package za.co.phumie.PostsService.service.postsInt;

import za.phumie.shared.appdtos.ResponseDto;
import za.phumie.shared.appmodels.Comment;

@FunctionalInterface
public interface IComments {
    ResponseDto postComment(Comment comment);
}
