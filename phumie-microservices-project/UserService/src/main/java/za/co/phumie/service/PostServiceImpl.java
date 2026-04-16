package za.co.phumie.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.phumie.dto.CommentDto;
import za.co.phumie.dto.PostDto;
import za.co.phumie.exception.EmptyUsernamePostException;
import za.co.phumie.exception.PostNotFound;
import za.co.phumie.mapper.CommentMapper;
import za.co.phumie.mapper.PostMapper;
import za.co.phumie.model.Post;
import za.co.phumie.repository.CommentRepository;
import za.co.phumie.repository.PostRepository;
import za.co.phumie.service.usersint.IPost;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<PostDto> getRandomPostsForWelcomeScreen() {
        return postsRepository.findAll().stream().limit(5)
                .map(PostMapper::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long postId) {
        Post entity = postsRepository.getPostByPostId(postId).orElseThrow(
                () -> new PostNotFound(String.format(POST_NOT_FOUND, postId))
        );
        return PostMapper.mapEntityToDto(entity);
    }

    @Override
    public List<CommentDto> getPostComments(long postId) {
        List<CommentDto> allComments = commentsRepository
                .findAllById(Collections.singleton(postId))
                .stream().map(CommentMapper::toDto).collect(Collectors.toList());
        return allComments;
    }

    @Override
    public void createPost(PostDto post) {
        if(post == null){
            throw new EmptyUsernamePostException(INVALID_POST_REQUEST);
        }
        postsRepository.save(PostMapper.mapDtoToEntity(post));
    }

    @Override
    public Page<Post> getUserPostsByUsernameOrId(long authorId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("time_stamp").descending());
        return postsRepository.getPostByAuthorUserId(authorId, pageable);
    }
}