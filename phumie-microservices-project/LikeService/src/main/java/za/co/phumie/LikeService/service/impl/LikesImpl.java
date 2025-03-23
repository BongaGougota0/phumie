package za.co.phumie.LikeService.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import za.co.phumie.LikeService.model.FollowDto;
import za.co.phumie.LikeService.model.LikeDto;
import za.co.phumie.LikeService.service.ILikes;

@Service
public class LikesImpl implements ILikes {

    private KafkaTemplate<String, LikeDto> likesTemplate;
    private KafkaTemplate<String, FollowDto> followTemplate;

    @Override
    public boolean followUser(FollowDto followDto) {
        followTemplate.send("FOLLOW", followDto);
        return true;
    }

    @Override
    public boolean unfollowUser(FollowDto followDto) {
        followTemplate.send("FOLLOW", followDto);
        return true;
    }

    @Override
    public String likePostByPostId(LikeDto likeDto) {
        likesTemplate.send("LIKE", likeDto);
        return "";
    }
}
