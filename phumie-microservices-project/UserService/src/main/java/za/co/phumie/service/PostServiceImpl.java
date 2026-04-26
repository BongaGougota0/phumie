package za.co.phumie.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import za.phumie.shared.appdtos.CommentDto;
import za.phumie.shared.appdtos.PostDto;
import za.co.phumie.exception.EmptyUsernamePostException;
import za.co.phumie.exception.PostNotFound;
import za.co.phumie.mapper.CommentMapper;
import za.co.phumie.mapper.PostMapper;
import za.phumie.shared.appmodels.Post;
import za.co.phumie.repository.CommentRepository;
import za.co.phumie.repository.PostRepository;
import za.co.phumie.service.usersint.IPost;
import java.util.Collections;

@Service
public class PostServiceImpl implements IPost {
    public final String POST_NOT_FOUND = "Post with id %s not found";
    public final String INVALID_POST_REQUEST = "Invalid post request, username required";

    private final PostRepository postsRepository;
    private final CommentRepository commentsRepository;

    public PostServiceImpl(PostRepository postsRepository, CommentRepository commentsRepository) {
        this.postsRepository = postsRepository;
        this.commentsRepository = commentsRepository;
    }

    @Cacheable(value = "postEntitiesCache", key = "#postId")
    public Post getPostObjectById(long postId){
        return postsRepository.findPostByPostId(postId);
    }

    public Flux<PostDto> getRandomPostsForWelcomeScreen() {
        return postsRepository.findAll()
                .take(5)
                .map(PostMapper::mapEntityToDto);
    }

    @Override
    public PostDto getPostById(long postId) {
        Post entity = postsRepository.getPostByPostId(postId).orElseThrow(
                () -> new PostNotFound(String.format(POST_NOT_FOUND, postId))
        );
        return PostMapper.mapEntityToDto(entity);
    }

    @Override
    public Flux<CommentDto> getPostComments(long postId) {
        return commentsRepository
                .findAllById(Collections.singleton(postId))
                .map(CommentMapper::toDto);
    }

    @Override
    public void createPost(PostDto post) {
        if(post == null){
            throw new EmptyUsernamePostException(INVALID_POST_REQUEST);
        }
        postsRepository.save(PostMapper.mapDtoToEntity(post));
    }

    @Override
    public Flux<Post> getUserPostsByUsernameOrId(long authorId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("time_stamp").descending());
        return postsRepository.getPostByAuthorUserId(authorId, pageable);
    }
}