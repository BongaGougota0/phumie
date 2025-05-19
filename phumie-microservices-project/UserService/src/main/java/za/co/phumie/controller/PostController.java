package za.co.phumie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.phumie.service.PostServiceImpl;

@RestController
@RequestMapping(value = "/api/post")
public class PostController {
    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService){
        this.postService = postService;
    }
}
