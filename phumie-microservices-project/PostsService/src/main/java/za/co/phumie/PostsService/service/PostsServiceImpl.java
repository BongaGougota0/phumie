package za.co.phumie.PostsService.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.phumie.PostsService.controller.PostsController;
import za.co.phumie.PostsService.exception.EmptyUsernamePostException;
import za.co.phumie.PostsService.exception.PostNotFound;
import za.co.phumie.PostsService.mapper.CommentMapper;
import za.co.phumie.PostsService.mapper.PostMapper;
import za.co.phumie.PostsService.repository.CommentsRepository;
import za.co.phumie.PostsService.repository.PostsRepository;
import za.co.phumie.PostsService.service.postsInt.IPosts;
import za.phumie.shared.appdtos.CommentDto;
import za.phumie.shared.appdtos.PostDto;
import za.phumie.shared.appmodels.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Cacheable(value = "postEntitiesCache", key = "#postId")
    public Post getPostObjectById(long postId){
        return postsRepository.findById(postId).block();
    }

    public List<PostDto> getRandomPostsForWelcomeScreen() {
        return new ArrayList<>();
    }

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
