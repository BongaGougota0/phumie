package za.co.phumie.LikeService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.co.phumie.LikeService.entity.UserLikes;

public interface UserLikesRepo extends JpaRepository<UserLikes, Long> {
    @Query(nativeQuery = true, value = "")
    String followUserAction(@Param("userId") long userId, @Param("followerId") long followerId);

    @Query(nativeQuery = true, value = "")
    String postReaction(@Param("postId") long postId, @Param("userId") long userId, @Param("userAction") String userAction);

    @Query(nativeQuery = true, value = "")
    String postReactionRemove(@Param("postId") long postId, @Param("userId") long userId);

    @Query(nativeQuery = true, value = "")
    String removeFollowing(@Param("userId") long userId, @Param("followerId") long followerId);
}
