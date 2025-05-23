package za.co.phumie.PostsService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import za.co.phumie.PostsService.dto.CommentDto;
import za.co.phumie.PostsService.dto.PostDto;
import za.co.phumie.PostsService.dto.ResponseDto;
import za.co.phumie.PostsService.mapper.CommentMapper;
import za.co.phumie.PostsService.model.Comment;
import za.co.phumie.PostsService.model.Post;
import za.co.phumie.PostsService.repository.CommentsRepository;
import za.co.phumie.PostsService.service.PostsServiceImpl;
import za.co.phumie.PostsService.service.postsInt.IComments;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostsController {
    Logger logger = LoggerFactory.getLogger(PostsController.class);

    private final PostsServiceImpl postsServiceImpl;
    private final CommentsRepository commentsRepository;

    public PostsController(PostsServiceImpl postsServiceImpl,
                           CommentsRepository commentsRepository) {
        this.postsServiceImpl = postsServiceImpl;
        this.commentsRepository = commentsRepository;
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

    @PostMapping("")
    public ResponseEntity<ResponseDto> createPost(@RequestBody Post post) {
        postsServiceImpl.createPost(post);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Post created");
        responseDto.setStatus("success");
        responseDto.setTimestamp(LocalDateTime.now());
        responseDto.setMicroserviceName("posts_microservice");
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/comment")
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
        comment.setPost(postsServiceImpl.getPostObjectById(postId));
        return ResponseEntity.ok().body(comments.postComment(comment));
    }

    @GetMapping("/user/{pageNumber}")
    public ResponseEntity<Page<Post>> geUserPosts(@RequestParam("username") String username, @PathVariable int pageNumber) {
        long authorId = transformUsernameToAuthorId(username);
        Page<Post> posts = postsServiceImpl.getUserPostsByUsernameOrId(authorId, pageNumber);
        return ResponseEntity.ok().body(posts);
    }

    public static long transformUsernameToAuthorId(String username) {
        RestTemplate restTemplate = new RestTemplate();
        try{
            String url = "http://localhost:8080/api/users/get?username={username}";
            ResponseEntity<Long> response = restTemplate.getForEntity(url, Long.class, username);
            return response.getBody();
        }catch (HttpClientErrorException.NotFound e){
            throw new IllegalArgumentException("Illegal arguments exception \n"+ e.getMessage());
        }
    }
}
