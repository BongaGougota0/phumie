package za.co.phumie.PostsService.service;

import org.springframework.stereotype.Service;
import za.co.phumie.PostsService.model.Post;
import za.co.phumie.PostsService.repository.PostsRepository;
import za.co.phumie.PostsService.service.postsInt.PostsInterface;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostsServiceImpl implements PostsInterface {
    private final PostsRepository postsRepository;

    public PostsServiceImpl(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public List<Post> getRandomPostsForWelcomeScreen() {
        return new ArrayList<Post>();
    }

    @Override
    public Post getPostById(int id) {
        return null;
    }

    @Override
    public Post createPost(Post post) {
        return null;
    }

    public List<Post> geUserPosts(String userEmail) {
        return new ArrayList<>();
    }
}
