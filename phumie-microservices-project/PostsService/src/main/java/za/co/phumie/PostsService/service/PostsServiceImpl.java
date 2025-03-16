package za.co.phumie.PostsService.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.phumie.PostsService.dto.CommentDto;
import za.co.phumie.PostsService.dto.PostDto;
import za.co.phumie.PostsService.exception.EmptyUsernamePostException;
import za.co.phumie.PostsService.exception.PostNotFound;
import za.co.phumie.PostsService.mapper.CommentMapper;
import za.co.phumie.PostsService.mapper.PostMapper;
import za.co.phumie.PostsService.model.Post;
import za.co.phumie.PostsService.repository.CommentsRepository;
import za.co.phumie.PostsService.repository.PostsRepository;
import za.co.phumie.PostsService.service.postsInt.IPosts;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostsServiceImpl implements IPosts {
    public final String POST_NOT_FOUND = "Post with id %s not found";
    public final String INVALID_POST_REQUEST = "Invalid post request, username required";

    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;

    public PostsServiceImpl(PostsRepository postsRepository, CommentsRepository commentsRepository) {
        this.postsRepository = postsRepository;
        this.commentsRepository = commentsRepository;
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
    public void createPost(Post post) {
        if(post == null){
            throw new EmptyUsernamePostException(INVALID_POST_REQUEST);
        }
        post.setTimeStamp(LocalDateTime.now());
        postsRepository.save(post);
    }

    @Override
    public Page<Post> getUserPostsByUsernameOrId(long authorId, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.by("time_stamp").descending());
        return postsRepository.getPostByAuthorUserId(authorId, pageable);
    }
}
