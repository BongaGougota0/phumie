package za.co.phumie.LikeService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.phumie.LikeService.model.FollowDto;
import za.co.phumie.LikeService.model.LikeDto;
import za.co.phumie.LikeService.service.ILikes;

@RestController
@RequestMapping(value = "/api/like")
public class LikesController {

    private final ILikes iLikes;

    public LikesController(ILikes iLikes) {
        this.iLikes = iLikes;
    }

    @PostMapping("")
    public ResponseEntity<String> likePost(@RequestBody LikeDto likeDto){
        String message = iLikes.likePostByPostId(likeDto);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/unlike")
    public ResponseEntity<String> unlikePost(@RequestBody LikeDto likeDto){
        String message = iLikes.likePostByPostId(likeDto);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/follow")
    public ResponseEntity<Boolean> followUser(@RequestBody FollowDto followDto){
        boolean message = iLikes.followUser(followDto);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/unfollow")
    public ResponseEntity<Boolean> unfollowUser(@RequestBody FollowDto followDto){
        boolean message = iLikes.unfollowUser(followDto);
        return ResponseEntity.ok().body(message);
    }
}
