package za.co.likesService.LikeAPI.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import za.co.likesService.LikeAPI.repo.LikeRepo;
import za.co.likesService.LikeAPI.repo.PostRepo;
import za.phumie.shared.mapper.ApplicationMapper;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LikesService {

    Sinks.Many<ApplicationMapper.PostLikeDto> sink;
    private final LikeRepo likeRepo;
    private final PostRepo postRepo;

    public LikesService(LikeRepo likeRepo, PostRepo postRepo, Sinks.Many<ApplicationMapper.PostLikeDto> sink) {
        this.likeRepo = likeRepo;
        this.postRepo = postRepo;
        this.sink = sink;
    }

    @PostConstruct
    public void onInit() {
        this.sink.asFlux()
                .buffer(Duration.ofSeconds(10))
                .map(like -> like.stream().collect(Collectors.groupingBy(
                        ApplicationMapper.PostLikeDto::postId
                )))
                .flatMap(this::writeLikes)
                .then();
    }

    private Mono<Void> writeLikes(Map<Long, List<ApplicationMapper.PostLikeDto>> likes) {
        return Flux
                .fromIterable(likes.entrySet())
                .flatMap(p -> incrementPostLikeCount(p.getKey(),
                        p.getValue().size()).thenReturn(p))
                .flatMap(listOfLikes -> {
                    var list = listOfLikes
                            .getValue()
                            .stream()
                            .map(ApplicationMapper::toPostLikeEntity);
                    return this.likeRepo.saveAll(list.toList());
                })
                .then();
    }

    public void addUserLike(ApplicationMapper.PostLikeDto likeDTO) {
        this.sink.tryEmitNext(likeDTO);
    }

    private Mono<Void> incrementPostLikeCount(long postId, int countIncrement) {
        return this.postRepo.findById(postId)
                .flatMap(post -> {
                    log.debug("incrementing post on #incrementMethod{}", post);
                    post.setLikeCount(post.getLikeCount() + countIncrement);
                    return this.postRepo.save(post);
                })
//                does not save.
//                .doOnNext(post -> post.setLikeCount(post.getLikeCount()+countIncrement))
                .then();
    }
}
