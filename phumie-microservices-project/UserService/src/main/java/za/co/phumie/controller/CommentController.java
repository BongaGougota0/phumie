package za.co.phumie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.phumie.dto.CommentDto;
import za.co.phumie.dto.ResponseDto;
import za.co.phumie.model.Comment;
import za.co.phumie.service.CommentServiceImpl;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService){
        this.commentService = commentService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getPostComment(@PathVariable("postId") long postId) {
        var comments = commentService.getPostComments(postId);
        return new ResponseEntity<>(comments,  HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto> postComment(@RequestBody Comment comment){
        var response = commentService.postComment(comment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
