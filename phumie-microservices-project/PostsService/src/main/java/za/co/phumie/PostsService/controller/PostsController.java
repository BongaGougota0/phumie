package za.co.phumie.PostsService.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.phumie.PostsService.model.Post;
import za.co.phumie.PostsService.service.PostsServiceImpl;
import java.util.List;

@RestController
@RequestMapping(path = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostsController {

    private final PostsServiceImpl postsServiceImpl;

    public PostsController(PostsServiceImpl postsServiceImpl) {
        this.postsServiceImpl = postsServiceImpl;
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> getWelcomeFeedPosts(){
        List<Post> generalPosts = postsServiceImpl.getRandomPostsForWelcomeScreen();
        return ResponseEntity.ok().body(generalPosts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        Post post = postsServiceImpl.getPostById(Math.toIntExact(postId));
        return ResponseEntity.ok().body(post);
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        postsServiceImpl.createPost(post);
        return ResponseEntity.ok().body(post);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Post>> geUserPosts(@RequestParam("email") String userEmail){
        List<Post> posts = postsServiceImpl.geUserPosts(userEmail);
        return ResponseEntity.ok().body(posts);
    }
}
