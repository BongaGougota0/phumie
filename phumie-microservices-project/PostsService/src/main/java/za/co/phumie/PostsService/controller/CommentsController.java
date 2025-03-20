package za.co.phumie.PostsService.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.phumie.PostsService.dto.CommentDto;
import za.co.phumie.PostsService.dto.ResponseDto;
import za.co.phumie.PostsService.mapper.CommentMapper;
import za.co.phumie.PostsService.model.Comment;
import za.co.phumie.PostsService.repository.CommentsRepository;
import za.co.phumie.PostsService.service.PostsServiceImpl;
import za.co.phumie.PostsService.service.postsInt.IComments;
import java.time.LocalDateTime;

@RestController
@RequestMapping(name ="/api/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentsController {

    private final CommentsRepository commentsRepository;
    private final PostsServiceImpl postsService;

    public CommentsController(CommentsRepository commentsRepository, PostsServiceImpl postsService) {
        this.commentsRepository = commentsRepository;
        this.postsService = postsService;
    }

    @PostMapping("/post")
    public ResponseEntity<ResponseDto> postComment(@RequestParam("postId") long postId, @RequestBody CommentDto commentDto){
        IComments comments = comment1 -> {
            commentsRepository.save(comment1);
            ResponseDto responseDto = new ResponseDto();
            responseDto.setTimestamp(LocalDateTime.now());
            responseDto.setMicroserviceName("post-service");
            responseDto.setStatus("ok");
            return responseDto;
        };
        Comment comment = CommentMapper.toEntity(commentDto);
        comment.setPost(postsService.getPostObjectById(postId));
        return ResponseEntity.ok().body(comments.postComment(comment));
    }
}
