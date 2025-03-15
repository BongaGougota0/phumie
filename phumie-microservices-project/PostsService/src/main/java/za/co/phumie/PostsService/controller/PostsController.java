package za.co.phumie.PostsService.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.phumie.PostsService.dto.PostDto;
import za.co.phumie.PostsService.dto.ResponseDto;
import za.co.phumie.PostsService.model.Post;
import za.co.phumie.PostsService.service.PostsServiceImpl;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostsController {

    private final PostsServiceImpl postsServiceImpl;

    public PostsController(PostsServiceImpl postsServiceImpl) {
        this.postsServiceImpl = postsServiceImpl;
    }

    @GetMapping("")
    public ResponseEntity<List<PostDto>> getWelcomeFeedPosts(){
        List<PostDto> generalPosts = postsServiceImpl.getRandomPostsForWelcomeScreen();
        return ResponseEntity.ok().body(generalPosts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        PostDto post = postsServiceImpl.getPostById(Math.toIntExact(postId));
        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/post")
    public ResponseEntity<ResponseDto> createPost(@RequestBody Post post) {
        postsServiceImpl.createPost(post);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Post created");
        responseDto.setStatus("success");
        responseDto.setTimestamp(LocalDateTime.now());
        responseDto.setMicroserviceName("posts_microservice");
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Post>> geUserPosts(@RequestParam("email") String userEmail){
        List<Post> posts = postsServiceImpl.geUserPosts(userEmail);
        return ResponseEntity.ok().body(posts);
    }
}
