package za.co.phumie.service.usersint;

import za.phumie.shared.appdtos.ResponseDto;
import za.phumie.shared.appmodels.Comment;

@FunctionalInterface
public interface IComment {
    ResponseDto postComment(Comment comment);
}
