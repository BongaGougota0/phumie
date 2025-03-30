package za.co.phumie.LikeService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.phumie.LikeService.entity.UserLikes;
import za.co.phumie.LikeService.model.LikeDto;
import java.util.List;

@Repository
public interface UserLikesRepo extends JpaRepository<UserLikes, Long> {

    @Query(nativeQuery = true, value = "INSERT INTO likes (user_id, post_id, user_action) VALUES (:userId, :postId, :userAction)")
    String postReaction(@Param("postId") long postId, @Param("userId") long userId, @Param("userAction") String userAction);

    @Query(nativeQuery = true, value = "DELTE FROM likes WHERE user_id =: userId AND post_id =: postId")
    String postReactionRemove(@Param("postId") long postId, @Param("userId") long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM likes WHERE user_id =: userId")
    List<LikeDto> findUserLikesByUserId(@Param("userId") Long userId);
}
