package za.co.phumie.LikeService.service.impl;

import org.springframework.stereotype.Service;
import za.co.phumie.LikeService.model.FollowDto;
import za.co.phumie.LikeService.model.LikeDto;
import za.co.phumie.LikeService.repository.UserLikesRepo;
import za.co.phumie.LikeService.service.IReactions;

@Service
public class ReactionsImpl implements IReactions {
    private final UserLikesRepo userLikesRepo;
    public ReactionsImpl(UserLikesRepo userLikesRepo) {
        this.userLikesRepo = userLikesRepo;
    }
    @Override
    public String handlePostReactions(LikeDto likeDto) {
        return likeDto.userAction() == "LIKE" ? userLikesRepo
                .postReaction(likeDto.postId(), likeDto.userId(), likeDto.userAction()) :
                userLikesRepo.postReactionRemove(likeDto.postId(), likeDto.userId());
    }

    @Override
    public String handleFollowReactions(FollowDto followDto) {

//        return followDto.userAction() == "FOLLOW" ? userLikesRepo
//                .followUserAction(followDto.userId(), followDto.followerId()) :
//                userLikesRepo.removeFollowing(followDto.userId(), followDto.followerId());
        return "";
    }
}
