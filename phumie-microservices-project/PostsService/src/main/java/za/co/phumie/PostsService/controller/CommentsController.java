package za.co.phumie.PostsService.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.phumie.PostsService.dto.ResponseDto;
import za.co.phumie.PostsService.model.Comment;
import za.co.phumie.PostsService.repository.CommentsRepository;
import za.co.phumie.PostsService.repository.PostsRepository;
import za.co.phumie.PostsService.service.postsInt.IComments;

@RestController
@RequestMapping(name="/api/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentsController {

    private final CommentsRepository commentsRepository;
    private final PostsRepository postsRepository;

    public CommentsController(CommentsRepository commentsRepository, PostsRepository postsRepository) {
        this.commentsRepository = commentsRepository;
        this.postsRepository = postsRepository;
    }

    @PostMapping("/post")
    public ResponseEntity<ResponseDto> postComment(@RequestParam("postId") long postId, @RequestBody Comment comment){
        IComments comments = comment1 -> {
            commentsRepository.save(comment1);
            ResponseDto responseDto = new ResponseDto();
            return responseDto;
        };
        comment.setAuthorUserId(PostsController.transformUsernameToAuthorId(comment.getAuthorUsername()));
        comment.setPost(postsRepository.findPostByPostId(postId));
        return ResponseEntity.ok().body(comments.postComment(comment));
    }
}
