package za.co.phumie.service.usersint;

import za.co.phumie.dto.FollowerDto;
import za.co.phumie.model.Follower;
import java.util.List;
import java.util.Map;

public interface IFollower {
    /**
     *
     * @param subjectUserId current user
     * @return list of DTOs records the current user is following
     */
    List<FollowerDto> getCurrentUserFollower(long subjectUserId);

    /**
     *
     * @param subjectUserId current user id
     * @param followeeUserId user to follow
     */
    void followUser(long subjectUserId, long followeeUserId);

    /**
     *
     * @param subjectUserId this current user
     * @param followeeUserId user to unfollow
     */
    void unFollowUser(long subjectUserId, long followeeUserId);

    /**
     * Key - [following, followers]
     * @param subjectUserId - the current users id
     * @return data for current user, users following count and follower count
     */
    Map<String, Integer> getFollowersFollowingData(long subjectUserId);

    /**
     *
     * @param follower
     * @return
     */
    FollowerDto mapToDto(Follower follower);

    /**
     *
     * @param subjectId current user id
     * @param followeeId use this map Follower to PhumieUser
     * @return Follower object to be saved to repo
     */
    Follower mapToEntity(long subjectId, long followeeId);
}
