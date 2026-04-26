package za.co.likesService.LikeAPI.controller;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import za.phumie.shared.appdtos.LikeDTO;
import za.co.likesService.LikeAPI.service.impl.LikesServiceImpl;

@RestController
@RequestMapping(value = "api/likes")
public class LikesController {

    private final LikesServiceImpl likesService;

    public LikesController(LikesServiceImpl likesService) {
        this.likesService = likesService;
    }

    @PostMapping
    public Mono<Void> likePost(@RequestBody Mono<LikeDTO> likeDTO) {
        return likesService.addLike(likeDTO);
    }
}
