package za.co.likesService.LikeAPI.service;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import za.co.likesService.LikeAPI.dto.LikeDTO;
import java.time.Duration;
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
                        LikeDTO::getPostId, Collectors.counting()
                )))
                .flatMap(this::writeLikes)
                .then();
    }

    private Mono<Void> writeLikes(Map<Integer, Long> likes) {
        return Flux
                .fromIterable(likes.entrySet())
                .then();
    }

    public void addUserLike(LikeDTO likeDTO) {
        this.sink.tryEmitNext(likeDTO);
    }
}
