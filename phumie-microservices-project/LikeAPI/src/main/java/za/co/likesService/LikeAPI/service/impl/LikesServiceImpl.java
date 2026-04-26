package za.co.likesService.LikeAPI.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import za.phumie.shared.appdtos.LikeDTO;
import za.co.likesService.LikeAPI.service.LikesService;

@Service
public class LikesServiceImpl extends LikesService {

    public Mono<Void> addLike(Mono<LikeDTO> likeDTO) {
       return Mono.just(likeDTO.then())
               .thenReturn(likeDTO.doFirst(() -> addUserLike(likeDTO.block())))
               .then();
    }
}
