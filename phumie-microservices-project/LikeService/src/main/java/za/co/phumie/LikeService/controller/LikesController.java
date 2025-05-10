package za.co.phumie.LikeService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.phumie.LikeService.model.FollowDto;
import za.co.phumie.LikeService.model.LikeDto;
import za.co.phumie.LikeService.service.ILikes;
import java.util.List;

@RestController
@RequestMapping(value = "/api/likes")
public class LikesController {

    private final ILikes iLikes;

    public LikesController(ILikes iLikes) {
        this.iLikes = iLikes;
    }

    @GetMapping("/{userId}")
    ResponseEntity<List<LikeDto>> getUserLikes(@PathVariable("userId") long userId){
        List<LikeDto> userLikes = iLikes.getUserLikesByUserId(userId);
        return ResponseEntity.ok().body(userLikes);
    }

    @PostMapping("/like")
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
