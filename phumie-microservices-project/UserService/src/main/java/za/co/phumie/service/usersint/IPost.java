package za.co.phumie.service.usersint;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import za.phumie.shared.appdtos.CommentDto;
import za.phumie.shared.appdtos.PostDto;
import za.phumie.shared.appmodels.Post;
import java.util.List;

public interface IPost {
    /**
     *
     * @return get most recent created post. use limit
     */
    Flux<PostDto> getRandomPostsForWelcomeScreen();

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
    Flux<CommentDto> getPostComments(long postId);

    /**
     *
     * @param post POJO write to db
     */
    void createPost(PostDto post);


    /**
     *
     * @param authorId fetch posts from transformed username to authorId (1st check cache)
     * @param pageNumber paginated results of user posts with their postIds
     * @return
     */
    Flux<Post> getUserPostsByUsernameOrId(long authorId, int pageNumber);
}
