package za.co.likesService.LikeAPI.controller;

import org.springframework.web.bind.annotation.*;
import za.co.likesService.LikeAPI.service.LikesService;
import za.phumie.shared.mapper.ApplicationMapper;

@RestController
@RequestMapping(value = "api/likes")
public class LikesController {

    private final LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping
    public void likePost(@RequestBody ApplicationMapper.PostLikeDto likeDTO) {
        likesService.addUserLike(likeDTO);
//        return Mono.just(obj).then();
    }
}
