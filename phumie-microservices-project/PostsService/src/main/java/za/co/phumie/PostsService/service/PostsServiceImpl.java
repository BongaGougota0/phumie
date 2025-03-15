package za.co.phumie.PostsService.service;

import org.hibernate.query.Page;
import org.springframework.stereotype.Service;
import za.co.phumie.PostsService.dto.CommentDto;
import za.co.phumie.PostsService.dto.PostDto;
import za.co.phumie.PostsService.exception.EmptyUsernamePostException;
import za.co.phumie.PostsService.model.Post;
import za.co.phumie.PostsService.repository.PostsRepository;
import za.co.phumie.PostsService.service.postsInt.IPosts;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostsServiceImpl implements IPosts {

    private final PostsRepository postsRepository;

    public PostsServiceImpl(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public List<PostDto> getRandomPostsForWelcomeScreen() {
        return new ArrayList<PostDto>();
    }

    @Override
    public PostDto getPostById(int postId) {
        return null;
    }

    @Override
    public List<CommentDto> getPostComments(int postId) {
        return List.of();
    }

    @Override
    public void createPost(Post post) {
        if(post == null){
            throw new EmptyUsernamePostException("Invalid post request, username required");
        }
        post.setTimeStamp(LocalDateTime.now());
        postsRepository.save(post);
    }

    public List<Post> geUserPosts(String userEmail) {
        return new ArrayList<>();
    }

    @Override
    public Page getUserPostsByUsername(String username) {
        return null;
    }
}
