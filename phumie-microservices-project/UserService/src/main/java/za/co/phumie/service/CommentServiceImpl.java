package za.co.phumie.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.phumie.dto.CommentDto;
import za.co.phumie.dto.ResponseDto;
import za.co.phumie.mapper.CommentMapper;
import za.co.phumie.model.Comment;
import za.co.phumie.repository.CommentRepository;
import za.co.phumie.repository.PostRepository;
import za.co.phumie.service.usersint.IComment;
import java.util.List;

@Service
public class CommentServiceImpl implements IComment {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<CommentDto> getPostComments(Long postId) {
         List<Comment> comments = commentRepository.getPostComments(postId);
         return comments.stream().map(CommentMapper::toDto).toList();
    }

    @Override
    public ResponseDto postComment(Comment comment) {
        var post = postRepository.findPostByPostId(comment.getCommentId());
        if(post != null) {
            ResponseDto responseDto = new ResponseDto();
            post.getComments().add(comment);
            postRepository.save(post);
            comment.setPost(post);
            commentRepository.save(comment);
            responseDto.setMessage("SUCCESS");
            return responseDto;
        }
        else {
            ResponseDto resp = new ResponseDto();
            resp.setMessage("Error post comment");
            resp.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            return resp;
        }
    }
}
