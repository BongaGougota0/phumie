package za.co.phumie.PostsService.service.postsInt;

import za.co.phumie.PostsService.model.Post;
import java.util.List;

public interface PostsInterface {
    public List<Post> getRandomPostsForWelcomeScreen();
    public Post getPostById(int id);
    public Post createPost(Post post);
    public List<Post> geUserPosts(String userEmail);
}
