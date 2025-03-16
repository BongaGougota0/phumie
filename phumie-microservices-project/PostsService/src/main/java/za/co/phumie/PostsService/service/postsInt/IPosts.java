package za.co.phumie.PostsService.service.postsInt;

import org.springframework.data.domain.Page;
import za.co.phumie.PostsService.dto.CommentDto;
import za.co.phumie.PostsService.dto.PostDto;
import za.co.phumie.PostsService.model.Post;
import java.util.List;

public interface IPosts {
    /**
     *
     * @return get most recent created post. use limit
     */
    List<PostDto> getRandomPostsForWelcomeScreen();

    /**
     *
     * @param postId - primary key of post/record
     * @return dto of post with current username
     */
    PostDto getPostById(long postId);

    /**
     *
     * @param postId -unique identifier for this posts.
     * @return - all comments that reference this post (post_id)
     */
    List<CommentDto> getPostComments(long postId);

    /**
     *
     * @param post POJO write to db
     */
    void createPost(Post post);


    /**
     *
     * @param authorId fetch posts from transformed username to authorId (1st check cache)
     * @param pageNumber paginated results of user posts with their postIds
     * @return
     */
    Page<Post> getUserPostsByUsernameOrId(long authorId, int pageNumber);
}
