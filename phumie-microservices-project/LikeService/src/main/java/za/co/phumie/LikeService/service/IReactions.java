package za.co.phumie.LikeService.service;

import za.co.phumie.LikeService.model.FollowDto;
import za.co.phumie.LikeService.model.LikeDto;

public interface IReactions {
    String handlePostReactions(LikeDto likeDto);
    String handleFollowReactions(FollowDto followDto);
}
