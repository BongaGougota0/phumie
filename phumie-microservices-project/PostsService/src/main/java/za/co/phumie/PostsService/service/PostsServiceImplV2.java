package za.co.phumie.PostsService.service;

import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import za.co.phumie.PostsService.controller.PostsController;
import za.co.phumie.PostsService.exception.EmptyUsernamePostException;
import za.co.phumie.PostsService.mapper.PostMapper;
import za.co.phumie.PostsService.repository.PostsRepository;
import za.co.phumie.PostsService.service.postsInt.IPosts;
import za.phumie.shared.appdtos.CommentDto;
import za.phumie.shared.appdtos.PostDto;
import za.phumie.shared.appmodels.Post;
import za.phumie.shared.template.CacheTemplate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostsServiceImplV2 extends CacheTemplate<Long, Post> implements IPosts {
    public final String INVALID_POST_REQUEST = "Invalid post request, username required";

    private final PostsRepository postsRepository;
    private final RMapReactive<Long, Post> map;

    public PostsServiceImplV2(PostsRepository postsRepository, RedissonReactiveClient client) {
        this.postsRepository = postsRepository;
        this.map = client.getMap("phumie_posts", new TypedJsonJacksonCodec(Long.class, Post.class));
    }

    @Override
    protected Mono<Post> getFromSource(Long key) {
        return postsRepository.findById(key);
    }

    @Override
    protected Mono<Post> getFromCache(Long key) {
        return this.map.get(key);
    }

    @Override
    protected Mono<Post> updateSource(Long key, Post entity) {
        return this.postsRepository.findById(key)
                .doOnNext(p -> entity.setPostId(key))
                .flatMap(p -> this.postsRepository.save(entity));
    }

    @Override
    protected Mono<Post> updateCache(Long key, Post entity) {
        return this.map.fastPut(key, entity).thenReturn(entity);
    }

    @Override
    protected Mono<Void> deleteFromSource(Long key) {
        return this.postsRepository.deleteById(key);
    }

    @Override
    protected Mono<Void> deleteFromCache(Long key) {
        return this.map.fastRemove(key).then();
    }

    @Override
    public List<PostDto> getRandomPostsForWelcomeScreen() {
        return List.of();
    }

    // Methods to override - Implementable.

    @Override
    public PostDto getPostById(long postId) {
        Post entity = postsRepository.findById(postId).block();
        return PostMapper.mapEntityToDto(entity);
    }

    @Override
    public List<CommentDto> getPostComments(long postId) {
//        List<CommentDto> allComments = commentsRepository
//                .findAllById(Collections.singleton(postId))
//                .stream().map(CommentMapper::toDto).collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public void createPost(Post post) {
        if(post == null){
            throw new EmptyUsernamePostException(INVALID_POST_REQUEST);
        }
//        post.setAuthorUserId(PostsController.transformUsernameToAuthorId(post.getAuthorUsername()));
        post.setCreatedAt(LocalDateTime.now());
        postsRepository.save(post);
    }

    @Override
    public Page<Post> getUserPostsByUsernameOrId(long authorId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("time_stamp").descending());
        return null;
    }
}
