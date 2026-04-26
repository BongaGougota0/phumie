package za.co.likesService.LikeAPI.service;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import za.phumie.shared.appdtos.LikeDTO;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class LikesService {

    Sinks.Many<LikeDTO> sink;

    public LikesService() {
        this.sink = Sinks.many().unicast().onBackpressureBuffer();
    }

    @PostConstruct
    public void onInit() {
        this.sink.asFlux()
                .buffer(Duration.ofSeconds(10))
                .map(like -> like.stream().collect(Collectors.groupingBy(
                        LikeDTO::getPostId
                )))
                .flatMap(this::writeLikes)
                .then();
    }

    private Mono<Void> writeLikes(Map<Integer, List<LikeDTO>> likes) {
        return Flux
                .fromIterable(likes.entrySet())
                // include some method that will persist likes to db while also incrementing postLike count.
                .then();
    }

    public void addUserLike(LikeDTO likeDTO) {
        this.sink.tryEmitNext(likeDTO);
    }
}
