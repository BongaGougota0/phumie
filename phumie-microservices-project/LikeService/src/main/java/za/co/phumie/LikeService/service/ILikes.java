package za.co.phumie.LikeService.service;

import za.co.phumie.LikeService.model.FollowDto;
import za.co.phumie.LikeService.model.LikeDto;
import java.util.List;

public interface ILikes {
    /**
     *
     * @param followDto - userAction - string FOLLOW
     * @return true on success, false on no result, already following or error happened
     */
    boolean followUser(FollowDto followDto);

    /**
     *
     * @param followDto - userAction - string UNFOLLOW
     * @return true on success, false on no result, or already unfollowed or error happened
     */
    boolean unfollowUser(FollowDto followDto);

    /**
     *
     * @param likeDto userAction - LIKE or UNLIKE
     * @return string message OK on success, FALSE already liked or an error happened
     */
    String likePostByPostId(LikeDto likeDto);

    List<LikeDto> getUserLikesByUserId(long userId);
}
