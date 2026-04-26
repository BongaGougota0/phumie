package za.co.phumie.PostsService.service;

import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import za.co.phumie.PostsService.repository.PostsRepository;
import za.phumie.shared.appmodels.Post;
import za.phumie.shared.template.CacheTemplate;

@Service
public class PostsServiceImplV2 extends CacheTemplate<Long, Post> {

    private final PostsRepository postsRepository;
    private final RMapReactive<Long, Post> map;

    public PostsServiceImplV2(PostsRepository postsRepository, RedissonReactiveClient client) {
        this.postsRepository = postsRepository;
        this.map = client.getMap("phumie_posts", new TypedJsonJacksonCodec(Long.class, Post.class));
    }

    @Override
    protected Mono<Post> getFromSource(Long key) {
        return null;
    }

    @Override
    protected Mono<Post> getFromCache(Long key) {
        return null;
    }

    @Override
    protected Mono<Post> updateSource(Long key, Post entity) {
        return null;
    }

    @Override
    protected Mono<Post> updateCache(Long key, Post entity) {
        return null;
    }

    @Override
    protected Mono<Void> deleteFromSource(Long key) {
        return null;
    }

    @Override
    protected Mono<Void> deleteFromCache(Long key) {
        return null;
    }
}
