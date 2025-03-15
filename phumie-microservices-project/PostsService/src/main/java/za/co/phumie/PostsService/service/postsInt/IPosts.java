package za.co.phumie.PostsService.service.postsInt;

import org.hibernate.query.Page;
import za.co.phumie.PostsService.dto.CommentDto;
import za.co.phumie.PostsService.dto.PostDto;
import za.co.phumie.PostsService.model.Post;
import java.util.List;

public interface IPosts {
    /**
     *
     * @return get most recent created post. use limit
     */
    public List<PostDto> getRandomPostsForWelcomeScreen();

    /**
     *
     * @param postId - primary key of post/record
     * @return dto of post with current username
     */
    public PostDto getPostById(int postId);

    /**
     *
     * @param postId -unique identifier for this posts.
     * @return - all comments that reference this post (post_id)
     */
    public List<CommentDto> getPostComments(int postId);

    /**
     *
     * @param post POJO write to db
     */
    public void createPost(Post post);

    /**
     *
     * @param username - fetch posts by username (1st check cache)
     * @return paginated results of user posts with their postIds
     */
    public Page getUserPostsByUsername(String username);
}
