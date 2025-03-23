package za.co.phumie.LikeService.model;

public record FollowDto(long userId, long followerId, String userAction) {
}
