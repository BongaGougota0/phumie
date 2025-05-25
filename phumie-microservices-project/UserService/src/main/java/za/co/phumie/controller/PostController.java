package za.co.phumie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.phumie.dto.PostDto;
import za.co.phumie.dto.ResponseDto;
import za.co.phumie.service.PostServiceImpl;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/v1/post")
public class PostController {
    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService){
        this.postService = postService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseDto> createPost(@RequestBody PostDto postDto){
        postService.createPost(postDto);
        var response = new ResponseDto();
        response.setMessage("Post created");
        response.setStatus("");
        response.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId){
        var post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
